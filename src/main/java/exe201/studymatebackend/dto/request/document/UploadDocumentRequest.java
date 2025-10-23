package exe201.studymatebackend.dto.request.document;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UploadDocumentRequest {

    @NotBlank(message = "Tên tài liệu không được để trống")
    private String documentName;

    @URL(message = "URL tài liệu không hợp lệ")
    private String documentURL;

    private String description;
}
