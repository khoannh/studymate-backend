package exe201.studymatebackend.dto.response.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateActionCoinResponse {
    private Integer actionID;
    private int actionCoin;
}
