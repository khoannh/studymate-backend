package exe201.studymatebackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Chung
    UNCATEGORIZED(1000, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    YOU_HAVE_NOT_PERMISSION(1001, "Bạn không có quyền thực hiện thao tác này", HttpStatus.BAD_REQUEST),

    // Account
    USERNAME_ALREADY_EXIST(2000, "Tên đăng nhập đã tồn tại", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXIST(2001, "Email đã tồn tại", HttpStatus.BAD_REQUEST),
    PASSWORD_DOES_NOT_MATCH(2002, "Mật khẩu và xác nhận mật khẩu không giống nhau", HttpStatus.BAD_REQUEST),
    USER_DOES_NOT_EXIST(2003, "Người dùng không tồn tại", HttpStatus.BAD_REQUEST),
    OLD_PASSWORD_IS_WRONG(2004, "Mật khẩu cũ không đúng", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(2005, "Tên đăng nhập hoặc mật khẩu không hợp lệ", HttpStatus.BAD_REQUEST),
    ACCOUNT_IS_INACTIVE(2006, "Tài khoản của bạn đã bị khóa", HttpStatus.BAD_REQUEST),
    FILE_UPLOAD_FAILED(2007, "Tải file thất bại", HttpStatus.BAD_REQUEST),
    FILE_NOT_FOUND(2008, "Không tìm thấy file", HttpStatus.BAD_REQUEST),
    // Room
    NOT_ENOUGH_COIN(3000, "Không đủ coin, vui lòng mua thêm", HttpStatus.BAD_REQUEST),
    ROOM_ALREADY_EXIST(3001, "Phòng học đã tồn tại", HttpStatus.BAD_REQUEST),
    ACCOUNT_ALREADY_JOINED_ROOM(3002, "Bạn đã tham gia phòng học này rồi", HttpStatus.BAD_REQUEST),
    ROOM_ALREADY_FULL(3003, "Phòng học đã đủ thành viên", HttpStatus.BAD_REQUEST),
    ROOM_DOES_NOT_EXIST(3004, "Phòng học không tồn tại", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_IN_ROOM(3005, "Bạn không phải là thành viên của phòng", HttpStatus.BAD_REQUEST),
    OWNER_CANNOT_LEAVE_ROOM(3006, "Chủ phòng không thể rời phòng, vui lòng chuyển quyền cho thành viên khác trước", HttpStatus.BAD_REQUEST),
    ROOM_IS_INACTIVE(3007, "Phòng này đã bị khóa", HttpStatus.BAD_REQUEST),
    // Action
    ACTION_ALREADY_EXIST(4000, "Hành động này đã tồn tại", HttpStatus.BAD_REQUEST),
    ACTION_DOES_NOT_EXIST(4001, "Hành động này không tồn tại", HttpStatus.BAD_REQUEST),
    // Package

    PACKAGE_ID_NOT_FOUND(5001, "Không tìm thấy gói", HttpStatus.BAD_REQUEST),
    //topic
    TOPIC_DOES_NOT_EXIST(6001, "Không tìm thấy chủ đề", HttpStatus.BAD_REQUEST),
    //report
    REPORT_NOT_FOUND(7001, "Không tìm thấy báo cáo", HttpStatus.BAD_REQUEST),


    //Document
    DOCUMENT_NAME_ALREADY_EXISTS(8001, "Tên tài liệu đã tồn tại", HttpStatus.BAD_REQUEST),
    DOCUMENT_URL_ALREADY_EXISTS(8002, "Đường dẫn tài liệu đã tồn tại", HttpStatus.BAD_REQUEST),
    DOCUMENT_NOT_FOUND(8003, "Không tìm thấy tài liệu nào", HttpStatus.BAD_REQUEST),


    ;
    private int detailCode;
    private String detailMessage;
    private HttpStatus httpCode;

}
