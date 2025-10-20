package exe201.studymatebackend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageType {
    CHAT("CHAT"),
    JOIN("JOIN"),
    LEAVE("LEAVE");
    private String value;
}
