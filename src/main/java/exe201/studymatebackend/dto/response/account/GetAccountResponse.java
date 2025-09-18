package exe201.studymatebackend.dto.response.account;

import exe201.studymatebackend.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GetAccountResponse {
    private Integer accountID;
    private String username;
    private String email;
    private Role role;
    private int token;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
}
