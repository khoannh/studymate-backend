package exe201.studymatebackend.dto.response.account;

import exe201.studymatebackend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllAccountResponse {
    private Integer accountID;
    private String username;
    private String email;
    private Role role;
    private int coin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;

}
