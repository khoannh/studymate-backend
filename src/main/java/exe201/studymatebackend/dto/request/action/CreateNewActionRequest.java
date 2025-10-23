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
    @NotBlank(message = "Tên hành động không được để trống")
    private String actionName;

    @NotNull(message = "Số lượng coin không được để trống")
    @PositiveOrZero(message = "Số lượng coin phải là số không âm")
    private int actionCoin;
}
