package exe201.studymatebackend.dto.response.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllMembersResponse {
    private Integer accountID;
    private String username;
    private LocalDateTime joinedAt;
}
