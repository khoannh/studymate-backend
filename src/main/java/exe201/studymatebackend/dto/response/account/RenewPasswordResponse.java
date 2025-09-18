package exe201.studymatebackend.dto.response.account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RenewPasswordResponse {
    private Integer accountID;
    private String message;
}