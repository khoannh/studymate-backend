package exe201.studymatebackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Chung
    UNCATEGORIZED(1000, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    // Account
    USERNAME_ALREADY_EXIST(2000, "Username is already registered", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXIST(2001, "Email is already registered", HttpStatus.BAD_REQUEST),
    PASSWORD_DOES_NOT_MATCH(2002, "Password does not match", HttpStatus.BAD_REQUEST),
    USER_DOES_NOT_EXIST(2003, "User does not exist", HttpStatus.BAD_REQUEST),
    OLD_PASSWORD_IS_WRONG(2004, "Old password is wrong", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(2005, "Username or password is wrong", HttpStatus.BAD_REQUEST),
    ACCOUNT_IS_INACTIVE(2006, "Your account is inactive", HttpStatus.BAD_REQUEST),
    FILE_UPLOAD_FAILED(2007, "File upload failed",HttpStatus.BAD_REQUEST)
    ,FILE_NOT_FOUND(2008, "File not found",HttpStatus.BAD_REQUEST),
    // Room
    NOT_ENOUGH_COIN(3000, "Not enough coin. Please buy for more", HttpStatus.BAD_REQUEST),
    ROOM_ALREADY_EXIST(3001, "Room already exist", HttpStatus.BAD_REQUEST),
    ACCOUNT_ALREADY_JOINED_ROOM(3002, "Account already joined this room", HttpStatus.BAD_REQUEST),
    ROOM_ALREADY_FULL(3003, "Room already full", HttpStatus.BAD_REQUEST),
    // Action
    ACTION_ALREADY_EXIST(4000, "Action already exist", HttpStatus.BAD_REQUEST),
    ACTION_DOES_NOT_EXIST(4001, "Action does not exist", HttpStatus.BAD_REQUEST),
    // Package
    PACKAGE_ID_NOT_FOUND(5001, "Package id not found", HttpStatus.BAD_REQUEST)

    ;
    private int detailCode;
    private String detailMessage;
    private HttpStatus httpCode;

}
