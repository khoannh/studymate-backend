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
    ;
    private int detailCode;
    private String detailMessage;
    private HttpStatus httpCode;

}
