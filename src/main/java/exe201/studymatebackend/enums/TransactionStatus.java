package exe201.studymatebackend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionStatus {

    PENDING("Pending"),
    PAID("Paid"),
    CANCELLED("Cancelled");
    private String value;
}
