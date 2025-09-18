package exe201.studymatebackend.dto.response.account;

import exe201.studymatebackend.enums.Role;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAccountResponse {
    private Integer accountID;
    private String email;

}
