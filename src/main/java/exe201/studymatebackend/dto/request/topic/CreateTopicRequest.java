package exe201.studymatebackend.dto.request.topic;

import lombok.Data;

@Data
public class CreateTopicRequest {
    private String topicName;
    private String description;
    private boolean isActive;
}