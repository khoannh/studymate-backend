package exe201.studymatebackend.dto.request.action;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateActionTokenRequest {

    @NotNull(message = "Action token cannot be null")
    private int actionToken;
}
