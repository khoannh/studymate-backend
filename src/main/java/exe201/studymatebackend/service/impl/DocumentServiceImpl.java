package exe201.studymatebackend.service.impl;

import exe201.studymatebackend.dto.request.document.UpdateDocumentRequest;
import exe201.studymatebackend.dto.request.document.UploadDocumentRequest;
import exe201.studymatebackend.dto.response.document.GetAllDocumentResponse;
import exe201.studymatebackend.dto.response.document.UpdateDocumentResponse;
import exe201.studymatebackend.dto.response.document.UploadDocumentResponse;
import exe201.studymatebackend.enums.RoomRole;
import exe201.studymatebackend.exception.AppException;
import exe201.studymatebackend.exception.ErrorCode;
import exe201.studymatebackend.pojo.Account;
import exe201.studymatebackend.pojo.Document;
import exe201.studymatebackend.pojo.Room;
import exe201.studymatebackend.repository.AccountRepository;
import exe201.studymatebackend.repository.AccountRoomRepository;
import exe201.studymatebackend.repository.DocumentRepository;
import exe201.studymatebackend.repository.RoomRepository;
import exe201.studymatebackend.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountRoomRepository accountRoomRepository;

    @Override
    @Transactional
    public UploadDocumentResponse uploadDocument(Integer roomID, UploadDocumentRequest request) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer accountID = account.getAccountID();
        Account uploader = accountRepository.findByAccountID(accountID);
        Room room = roomRepository.findByRoomID(roomID);
        if (room == null) {
            throw new AppException(ErrorCode.ROOM_DOES_NOT_EXIST);
        }
        if (documentRepository.findByDocumentName(request.getDocumentName()) != null) {
            throw new AppException(ErrorCode.DOCUMENT_NAME_ALREADY_EXISTS);
        }
        if (documentRepository.findByDocumentURL(request.getDocumentURL()) != null) {
            throw new AppException(ErrorCode.DOCUMENT_URL_ALREADY_EXISTS);
        }
        Document newDocument = new Document();
        newDocument.setDocumentName(request.getDocumentName());
        newDocument.setDocumentURL(request.getDocumentURL());
        newDocument.setRoom(room);
        newDocument.setUploader(uploader.getUsername());
        newDocument.setDescription(request.getDescription());
        newDocument.setUploadedAt(LocalDateTime.now());
        documentRepository.save(newDocument);
        return UploadDocumentResponse.builder()
                .documentID(newDocument.getDocumentID())
                .documentURL(newDocument.getDocumentURL())
                .documentName(newDocument.getDocumentName())
                .roomID(newDocument.getRoom().getRoomID())
                .description(newDocument.getDescription())
                .uploader(newDocument.getUploader())
                .uploadedAt(newDocument.getUploadedAt())
                .build();
    }

    @Override
    public GetAllDocumentResponse getAllDocuments(Integer roomID, int page, int size) {
        Room room = roomRepository.findByRoomID(roomID);
        if (room == null) {
            throw new AppException(ErrorCode.ROOM_DOES_NOT_EXIST);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "uploadedAt"));
        Page<Document> documentPage = documentRepository.findByRoom(room, pageable);
        if (documentPage == null) {
            throw new AppException(ErrorCode.DOCUMENT_NOT_FOUND);
        }
        List<GetAllDocumentResponse.DocumentResponse> documentResponses = documentPage.getContent().stream().map(doc -> GetAllDocumentResponse.DocumentResponse.builder()
                .documentID(doc.getDocumentID())
                .documentName(doc.getDocumentName())
                .documentURL(doc.getDocumentURL())
                .description(doc.getDescription())
                .uploader(doc.getUploader())
                .uploadedAt(doc.getUploadedAt())
                .build()).toList();
        return GetAllDocumentResponse.builder()
                .roomID(room.getRoomID())
                .pageNumber(documentPage.getNumber())
                .pageSize(documentPage.getSize())
                .totalElements(documentPage.getTotalElements())
                .totalPages(documentPage.getTotalPages())
                .isLastPage(documentPage.isLast())
                .documents(documentResponses)
                .build();
    }

    @Override
    @Transactional
    public UpdateDocumentResponse updateDocument(Integer documentID, UpdateDocumentRequest request) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer accountID = account.getAccountID();
        Account updater = accountRepository.findByAccountID(accountID);
        Document document = documentRepository.findByDocumentID(documentID);
        if (document == null) {
            throw new AppException(ErrorCode.DOCUMENT_NOT_FOUND);
        }
        if (!document.getUploader().equals(updater.getUsername())) {
            throw new AppException(ErrorCode.YOU_HAVE_NOT_PERMISSION);
        }
        if (!document.getDocumentName().equals(request.getDocumentName()) && !(request.getDocumentName().trim().isEmpty())) {
            if (documentRepository.findByDocumentName(request.getDocumentName()) != null) {
                throw new AppException(ErrorCode.DOCUMENT_NAME_ALREADY_EXISTS);
            }
            document.setDocumentName(request.getDocumentName());
        }
        if (!document.getDocumentURL().equals(request.getDocumentURL()) && !(request.getDocumentURL().trim().isEmpty())) {
            if (documentRepository.findByDocumentURL(request.getDocumentURL()) != null) {
                throw new AppException(ErrorCode.DOCUMENT_URL_ALREADY_EXISTS);
            }
            document.setDocumentURL(request.getDocumentURL());
        }

        if (!request.getDescription().trim().isEmpty()) {
            document.setDescription(request.getDescription());
        }
        documentRepository.save(document);
        return UpdateDocumentResponse.builder()
                .documentID(document.getDocumentID())
                .documentName(document.getDocumentName())
                .documentURL(document.getDocumentURL())
                .description(document.getDescription())
                .build();
    }

    @Override
    @Transactional
    public void deleteDocument(Integer documentID) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer accountID = account.getAccountID();
        Account deleter = accountRepository.findByAccountID(accountID);
        Document document = documentRepository.findByDocumentID(documentID);
        if (document == null) {
            throw new AppException(ErrorCode.DOCUMENT_NOT_FOUND);
        }
        Account ownerRoom = (accountRoomRepository.findByRoomAndRoomRole(document.getRoom(), RoomRole.OWNER)).getAccount();
        if ((deleter == ownerRoom) || ((deleter.getUsername().equals(document.getUploader())))) {
            documentRepository.delete(document);
        } else {
            throw new AppException(ErrorCode.YOU_HAVE_NOT_PERMISSION);
        }
    }


}
