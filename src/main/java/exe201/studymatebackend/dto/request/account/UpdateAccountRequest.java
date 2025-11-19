package exe201.studymatebackend.dto.request.account;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountRequest {
    @Email(message = "Email is not valid")
    private String email;

    private String bio;
}