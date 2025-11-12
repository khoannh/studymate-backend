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

    @NotBlank(message = "Tên phòng không được để trống")
    private String roomName;

    private String roomDescription;

    @NotNull(message = "Chủ đề không được để trống")
    private String topic;

    @NotNull(message = "Quyền riêng tư không được để trống")
    private boolean isPublic;

    private String roomPassword;

    @NotNull(message = "Số lượng thành viên tối đa không được để trống")
    private int maxNumberOfMembers;


}
