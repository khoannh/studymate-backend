package exe201.studymatebackend.service.impl;

import exe201.studymatebackend.dto.request.topic.CreateTopicRequest;
import exe201.studymatebackend.dto.request.topic.UpdateTopicRequest;
import exe201.studymatebackend.pojo.Topic;
import exe201.studymatebackend.repository.TopicRepository;
import exe201.studymatebackend.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @Override
    public Topic getTopicById(Integer id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));
    }

    @Override
    public Topic createTopic(CreateTopicRequest request) {
        if (topicRepository.existsByTopicName(request.getTopicName())) {
            throw new RuntimeException("Topic name already exists");
        }

        Topic topic = new Topic();
        topic.setTopicName(request.getTopicName());
        topic.setDescription(request.getDescription());
        topic.setActive(request.isActive());

        return topicRepository.save(topic);
    }

    @Override
    public Topic updateTopic(Integer id, UpdateTopicRequest request) {
        Topic topic = getTopicById(id);

        if (request.getTopicName() != null)
            topic.setTopicName(request.getTopicName());
        if (request.getDescription() != null)
            topic.setDescription(request.getDescription());

        topic.setActive(request.isActive());
        return topicRepository.save(topic);
    }

    @Override
    public void deleteTopic(Integer id) {
        Topic topic = getTopicById(id);
        topicRepository.delete(topic);
    }
}
