package exe201.studymatebackend.dto.response.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateNewActionResponse {
    private Integer actionID;
    private String actionName;
    private int actionToken;
}
