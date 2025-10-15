package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.request.topic.CreateTopicRequest;
import exe201.studymatebackend.dto.request.topic.UpdateTopicRequest;
import exe201.studymatebackend.dto.response.ApiResponse;
import exe201.studymatebackend.pojo.Topic;
import exe201.studymatebackend.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topic-management")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/topics")
    public ApiResponse<List<Topic>> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        return ApiResponse.<List<Topic>>builder()
                .code(HttpStatus.OK.value())
                .message("Retrieved topics successfully")
                .result(topics)
                .build();
    }

    @GetMapping("/topics/{id}")
    public ApiResponse<Topic> getTopicById(@PathVariable Integer id) {
        Topic topic = topicService.getTopicById(id);
        return ApiResponse.<Topic>builder()
                .code(HttpStatus.OK.value())
                .message("Retrieved topic successfully")
                .result(topic)
                .build();
    }

    @PostMapping("/topics")
    public ApiResponse<Topic> createTopic(@RequestBody CreateTopicRequest request) {
        Topic topic = topicService.createTopic(request);
        return ApiResponse.<Topic>builder()
                .code(HttpStatus.CREATED.value())
                .message("Created topic successfully")
                .result(topic)
                .build();
    }

    @PutMapping("/topics/{id}")
    public ApiResponse<Topic> updateTopic(@PathVariable Integer id, @RequestBody UpdateTopicRequest request) {
        Topic topic = topicService.updateTopic(id, request);
        return ApiResponse.<Topic>builder()
                .code(HttpStatus.OK.value())
                .message("Updated topic successfully")
                .result(topic)
                .build();
    }

    @DeleteMapping("/topics/{id}")
    public ApiResponse<String> deleteTopic(@PathVariable Integer id) {
        topicService.deleteTopic(id);
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Deleted topic successfully")
                .result("Deleted")
                .build();
    }
}
