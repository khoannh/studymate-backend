package exe201.studymatebackend.dto.request.account;

import lombok.Data;

@Data
public class RenewPasswordRequest {
    private String newPassword;
}