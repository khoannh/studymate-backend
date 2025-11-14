package exe201.studymatebackend.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import exe201.studymatebackend.dto.request.payment.CreatePaymentRequest;
import exe201.studymatebackend.service.PaymentTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin
public class PaymentTransactionController {

    private final PaymentTransactionService paymentService;

    // =========================
    // API tạo link thanh toán
    // =========================
    @PostMapping("/create-link")
    public ResponseEntity<CreatePaymentLinkResponse> createPaymentLink(
            @Valid @RequestBody CreatePaymentRequest request
    ) throws Exception {
        System.out.println("hello");
        CreatePaymentLinkResponse response = paymentService.createPaymentLink(request);
        return ResponseEntity.ok(response);
    }

    // =========================
    // API webhook nhận từ PayOS
    // =========================
    @PostMapping("/webhook")
    public ResponseEntity<Map<String, Object>> handleWebhook(
            @RequestBody ObjectNode webhookBody
    ) throws Exception {
        Map<String, Object> result = paymentService.handlePaymentWebhook(webhookBody);
        return ResponseEntity.ok(result);
    }
}

