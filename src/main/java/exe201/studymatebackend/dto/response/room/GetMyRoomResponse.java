package exe201.studymatebackend.dto.response.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetMyRoomResponse {

    private List<RoomInfo> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLastPage;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RoomInfo {
        private Integer roomID;
        private String roomName;
//        private String roomDescription;
//        private String topic;
//        private boolean isPublic;
//        private LocalDateTime createdAt;
//        private boolean isActive;
//        private int maxNumberOfMembers;
//        private int numberOfMembers;
    }
}
