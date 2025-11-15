package exe201.studymatebackend.dto.response.account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublicAccountProfileResponse {
    private Integer accountID;
    private String username;
    private String email;
    private Integer trustScore;
}
