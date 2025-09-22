package exe201.studymatebackend.dto.request.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomRequest {
//    @NotNull(message = "AccountID cannot be null")
//    private Integer accountID;

    @NotBlank(message = "Room name cannot be blank")
    private String roomName;

    private String roomDescription;

    @NotNull(message = "Topic cannot be null")
    private String topic;

    @NotNull(message = "Privacy cannot be null")
    private boolean isPublic;

    @NotNull(message = "Max number of members cannot be null ")
    private int maxNumberOfMembers;


}
