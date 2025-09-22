package exe201.studymatebackend.dto.response.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateActionTokenResponse {
    private Integer actionID;
    private int actionToken;
}
