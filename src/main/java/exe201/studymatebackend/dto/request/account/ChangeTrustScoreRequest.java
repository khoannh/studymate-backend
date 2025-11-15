package exe201.studymatebackend.dto.request.account;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeTrustScoreRequest {
    @NotNull
    private Integer by;     // ví dụ 5, 10
    private String reason;  // lý do
}
