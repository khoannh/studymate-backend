package exe201.studymatebackend.service.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import exe201.studymatebackend.dto.request.payment.CreatePaymentRequest;
import exe201.studymatebackend.enums.TransactionStatus;
import exe201.studymatebackend.exception.AppException;
import exe201.studymatebackend.exception.ErrorCode;
import exe201.studymatebackend.pojo.Account;
import exe201.studymatebackend.pojo.Package;
import exe201.studymatebackend.pojo.PaymentTransaction;
import exe201.studymatebackend.repository.AccountRepository;
import exe201.studymatebackend.repository.PackageRepository;
import exe201.studymatebackend.repository.PaymentTransactionRepository;
import exe201.studymatebackend.service.PaymentTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import vn.payos.PayOS;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkRequest;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse;
import vn.payos.model.v2.paymentRequests.PaymentLinkItem;
import vn.payos.model.webhooks.WebhookData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentTransactionServiceImpl implements PaymentTransactionService {

    private final PayOS payOS;
    private final PaymentTransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final PackageRepository packageRepository;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Override
    @Transactional
    public CreatePaymentLinkResponse createPaymentLink(CreatePaymentRequest dto) throws Exception {
        // 1. Lấy thông tin gói
        Package selectedPackage = packageRepository.findById(dto.getPackageID())
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_ID_NOT_FOUND));

        // 2. Lấy thông tin account
        Account account = accountRepository.findById(dto.getAccountID())
                .orElseThrow(() -> new AppException(ErrorCode.ACTION_DOES_NOT_EXIST));

        // 3. Tạo mã đơn hàng duy nhất
        long orderCode = System.currentTimeMillis();

        // 4. Tính số tiền
        long amount = selectedPackage.getPrice();

        // 5. Tạo items cho PayOS
        PaymentLinkItem item = PaymentLinkItem.builder()
                .name(selectedPackage.getName())
                .quantity(1)
                .price(amount)
                .build();

        String returnUrl = StringUtils.hasText(dto.getReturnUrl())
                ? dto.getReturnUrl()
                : frontendUrl + "/payment-success";
        String cancelUrl = StringUtils.hasText(dto.getCancelUrl())
                ? dto.getCancelUrl()
                : frontendUrl + "/payment-cancel";
        System.out.println("test ne");

        String description = "Don hang " + orderCode;
        CreatePaymentLinkRequest request = CreatePaymentLinkRequest.builder()
                .orderCode(orderCode)
                .amount(amount)
                .description(description)
                .returnUrl(returnUrl)
                .cancelUrl(cancelUrl)
                .items(List.of(item))
                .build();

        // 6. Lưu giao dịch PENDING
        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setOrderCode(orderCode);
        transaction.setAmount((int) amount);
        transaction.setDescription(description);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setAccount(account);
        transaction.setApackage(selectedPackage);
        transactionRepository.save(transaction);
        System.out.println("test ne2");
        // 7. Tạo link thanh toán
        try {
            // 7. Gọi PayOS
            return payOS.paymentRequests().create(request);
        } catch (Exception e) {
            // In lỗi chi tiết ra Console để xem
            System.err.println("LỖI PAYOS: " + e.getMessage());
            e.printStackTrace();

            // Ném lại lỗi để Controller biết
            throw new AppException(ErrorCode.UNCATEGORIZED);
        }
    }

    @Override
    @Transactional
    public Map<String, Object> handlePaymentWebhook(ObjectNode webhookBody) throws Exception {
        WebhookData webhookData = payOS.webhooks().verify(webhookBody);

        if (webhookData == null) {
            return Map.of("error", -1, "message", "Invalid webhook");
        }

        Long orderCode = webhookData.getOrderCode();

        if ("00".equals(webhookData.getCode()) && orderCode != null) {
            PaymentTransaction transaction = transactionRepository.findByOrderCode(orderCode);
            if (transaction == null) {
                return Map.of("error", -1, "message", "Transaction not found");
            }

            if (transaction.getStatus() == TransactionStatus.PENDING) {
                transaction.setStatus(TransactionStatus.PAID);
                transactionRepository.save(transaction);

                // Cộng token cho người dùng
                Account account = transaction.getAccount();
                Package pkg = transaction.getApackage();
                int currentCoin = account.getCoin() != null ? account.getCoin() : 0;
                int addedCoin = pkg.getTokenAmount() != null ? pkg.getTokenAmount() : 0;
                account.setCoin(currentCoin + addedCoin);
                accountRepository.save(account);
            }

            return Map.of("error", 0, "message", "Webhook processed successfully");
        }

        return Map.of("error", -1, "message", "Payment failed or invalid status");
    }
}
