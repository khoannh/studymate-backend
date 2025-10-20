package exe201.studymatebackend.dto.response.message;

import exe201.studymatebackend.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllMessageOfRoomResponse {
    private Integer roomID;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLastPage;
    private List<MessageResponse> messages;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MessageResponse {
        private Integer messageID;
        private String content;
        private Integer accountID;
        private String sender;
        private MessageType messageType;
        private LocalDateTime sentAt;
    }

}
