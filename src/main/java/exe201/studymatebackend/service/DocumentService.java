package exe201.studymatebackend.service;

import exe201.studymatebackend.dto.request.document.UpdateDocumentRequest;
import exe201.studymatebackend.dto.request.document.UploadDocumentRequest;
import exe201.studymatebackend.dto.response.document.GetAllDocumentResponse;
import exe201.studymatebackend.dto.response.document.UpdateDocumentResponse;
import exe201.studymatebackend.dto.response.document.UploadDocumentResponse;

public interface DocumentService {
    UploadDocumentResponse uploadDocument(Integer roomID, UploadDocumentRequest request);

    GetAllDocumentResponse getAllDocuments(Integer roomID, int page, int size);

    UpdateDocumentResponse updateDocument(Integer documentID, UpdateDocumentRequest request);

    void deleteDocument(Integer documentID);
}
