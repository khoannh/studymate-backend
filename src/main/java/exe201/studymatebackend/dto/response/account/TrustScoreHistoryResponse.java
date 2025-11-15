package exe201.studymatebackend.dto.response.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrustScoreHistoryResponse {
    private Integer delta;          // +5, -10...
    private String reason;          // lý do
    private LocalDateTime createdAt;
}