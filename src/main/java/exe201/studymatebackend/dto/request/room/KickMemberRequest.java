package exe201.studymatebackend.dto.request.room;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KickMemberRequest {

    @NotNull(message = "Thành viên không thể để trống")
    private Integer memberID;
}
