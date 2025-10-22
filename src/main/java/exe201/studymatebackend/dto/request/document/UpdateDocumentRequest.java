package exe201.studymatebackend.dto.request.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDocumentRequest {

    private String documentName;

    private String description;

    private String documentURL;
}
