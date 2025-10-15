package exe201.studymatebackend.service;

import exe201.studymatebackend.dto.request.topic.CreateTopicRequest;
import exe201.studymatebackend.dto.request.topic.UpdateTopicRequest;
import exe201.studymatebackend.pojo.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> getAllTopics();
    Topic getTopicById(Integer id);
    Topic createTopic(CreateTopicRequest request);
    Topic updateTopic(Integer id, UpdateTopicRequest request);
    void deleteTopic(Integer id);
}
