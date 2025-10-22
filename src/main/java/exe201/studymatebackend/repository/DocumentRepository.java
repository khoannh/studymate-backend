package exe201.studymatebackend.repository;

import exe201.studymatebackend.pojo.Document;
import exe201.studymatebackend.pojo.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Document findByDocumentName(String documentName);

    Document findByDocumentURl(String documentURl);

    Document findByDocumentID(Integer documentID);

    Page<Document> findByRoom(Room room, Pageable pageable);
}
