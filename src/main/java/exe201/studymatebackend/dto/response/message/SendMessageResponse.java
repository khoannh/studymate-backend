package exe201.studymatebackend.dto.response.message;

import exe201.studymatebackend.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendMessageResponse {
    private Integer messageID;
    private String content;
    private Integer roomID;
    private Integer accountID;
    private String sender;
    private MessageType messageType;
    private LocalDateTime sentAt;
}
