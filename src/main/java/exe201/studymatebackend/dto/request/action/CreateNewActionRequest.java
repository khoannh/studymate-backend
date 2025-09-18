package exe201.studymatebackend.dto.request.action;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewActionRequest {
    @NotBlank(message = "Action name cannot be blank")
    private String actionName;

    @NotNull(message = "Token cannot be null")
    @PositiveOrZero(message = "Number of token cannot be negative")
    private int actionToken;
}
