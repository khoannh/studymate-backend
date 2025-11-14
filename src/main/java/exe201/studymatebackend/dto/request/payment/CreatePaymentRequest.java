package exe201.studymatebackend.dto.request.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {
    private Integer packageID;
    private Integer accountID; // Người mua
    private String returnUrl;  // Trang đích sau khi thanh toán thành công
    private String cancelUrl;  // Trang đích khi hủy
}
