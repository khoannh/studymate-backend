package exe201.studymatebackend.dto.request.account;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTrustScoreRequest {
    @NotNull
    private Integer score;
    private String reason;
}
