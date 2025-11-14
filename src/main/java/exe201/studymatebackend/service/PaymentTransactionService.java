package exe201.studymatebackend.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import exe201.studymatebackend.dto.request.payment.CreatePaymentRequest;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse;

import java.util.Map;

public interface PaymentTransactionService {
    CreatePaymentLinkResponse createPaymentLink(CreatePaymentRequest dto) throws Exception;

    Map<String, Object> handlePaymentWebhook(ObjectNode webhookBody) throws Exception;
}
