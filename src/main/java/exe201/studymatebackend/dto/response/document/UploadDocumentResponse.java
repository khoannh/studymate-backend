package exe201.studymatebackend.dto.response.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadDocumentResponse {
    private Integer documentID;
    private Integer roomID;
    private String documentName;
    private String documentURL;
    private String description;
    private String uploader;
    private LocalDateTime uploadedAt;
}
