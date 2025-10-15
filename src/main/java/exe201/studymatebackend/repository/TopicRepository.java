package exe201.studymatebackend.repository;

import exe201.studymatebackend.pojo.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
    boolean existsByTopicName(String topicName);

}
