package exe201.studymatebackend.dto.request.account;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RenewPasswordRequest {

    @NotBlank(message = "Old password cannot be blank")
    private String oldPassword;

    @NotBlank(message = "New password cannot be blank")
    private String newPassword;

    @NotBlank(message = "Confirm new password cannot be blank")
    private String confirmNewPassword;
}