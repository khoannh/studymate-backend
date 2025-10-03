package exe201.studymatebackend.dto.request.action;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateActionCoinRequest {

    @NotNull(message = "Action coin cannot be null")
    private int actionCoin;
}
