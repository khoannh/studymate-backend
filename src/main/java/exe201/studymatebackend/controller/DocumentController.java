package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.request.document.UpdateDocumentRequest;
import exe201.studymatebackend.dto.request.document.UploadDocumentRequest;
import exe201.studymatebackend.dto.response.ApiResponse;
import exe201.studymatebackend.dto.response.document.GetAllDocumentResponse;
import exe201.studymatebackend.dto.response.document.UpdateDocumentResponse;
import exe201.studymatebackend.dto.response.document.UploadDocumentResponse;
import exe201.studymatebackend.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/document-management")
public class DocumentController {


    @Autowired
    private DocumentService documentService;

    @PostMapping("/{roomID}")
    public ApiResponse<UploadDocumentResponse> uploadDocument(@Valid @PathVariable Integer roomID, @RequestBody UploadDocumentRequest uploadDocumentRequest) {
        UploadDocumentResponse result = documentService.uploadDocument(roomID, uploadDocumentRequest);
        return ApiResponse.<UploadDocumentResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tải tài liệu lên thành công")
                .result(result)
                .build();
    }

    @GetMapping("/{roomID}")
    public ApiResponse<GetAllDocumentResponse> getAllDocumentsInRoom(@PathVariable Integer roomID,
                                                                     @RequestParam(defaultValue = "0") int pageNumber,
                                                                     @RequestParam(defaultValue = "10") int pageSize) {
        GetAllDocumentResponse result = documentService.getAllDocuments(roomID, pageNumber, pageSize);
        return ApiResponse.<GetAllDocumentResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách tài liệu thành công")
                .result(result)
                .build();
    }

    @PutMapping("/{documentID}")
    public ApiResponse<UpdateDocumentResponse> updateDocument(@PathVariable Integer documentID, @RequestBody UpdateDocumentRequest updateDocumentRequest) {
        UpdateDocumentResponse result = documentService.updateDocument(documentID, updateDocumentRequest);
        return ApiResponse.<UpdateDocumentResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật tài liệu thành công")
                .result(result)
                .build();
    }

    @DeleteMapping("/{documentID}")
    public ApiResponse<Void> deleteDocument(@PathVariable Integer documentID) {
        documentService.deleteDocument(documentID);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Xóa tài liệu thành công")
                .build();
    }

}
