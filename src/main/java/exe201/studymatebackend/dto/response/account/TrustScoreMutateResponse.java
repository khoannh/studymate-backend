package exe201.studymatebackend.dto.response.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrustScoreMutateResponse {
    private Integer accountId;
    private Integer oldScore;
    private Integer newScore;
    private Integer delta;
}