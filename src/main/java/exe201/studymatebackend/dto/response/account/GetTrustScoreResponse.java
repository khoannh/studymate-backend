package exe201.studymatebackend.dto.response.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTrustScoreResponse {
    private Integer accountId;
    private Integer trustScore;
}