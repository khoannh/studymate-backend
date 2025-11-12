package exe201.studymatebackend.dto.response.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllRoomResponse {
    private Integer roomID;
    private String roomName;
    private String roomDescription;
    private String topic;
    private boolean isPublic;
    private LocalDateTime createdAt;
    private boolean isActive;
    private int maxNumberOfMembers;
    private int numberOfMembers;
    private Boolean joined;

}
