package exe201.studymatebackend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoomRole {
    OWNER("OWNER"),
    MEMBER("MEMBER");
    private String value;

}
