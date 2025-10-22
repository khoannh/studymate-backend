package exe201.studymatebackend.dto.response.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllDocumentResponse {
    private Integer roomID;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLastPage;
    private List<DocumentResponse> documents;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DocumentResponse {
        private Integer documentID;
        private String documentName;
        private String documentURl;
        private String description;
        private String uploader;
        private LocalDateTime uploadedAt;
    }

}
