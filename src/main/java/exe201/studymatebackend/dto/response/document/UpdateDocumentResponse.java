package exe201.studymatebackend.dto.response.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDocumentResponse {
    private Integer documentID;
    private String documentName;
    private String description;
    private String documentURL;
}
