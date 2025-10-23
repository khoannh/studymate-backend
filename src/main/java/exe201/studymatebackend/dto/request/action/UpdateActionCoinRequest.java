package exe201.studymatebackend.dto.request.action;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateActionCoinRequest {

    @NotNull(message = "Số lượng coin không được để trống")
    private int actionCoin;
}
