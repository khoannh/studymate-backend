package exe201.studymatebackend.dto.request.quiz;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenerateQuizRequest {
    @NotBlank(message = "Tên bài quiz không thể để trống")
    private String quizSetName;

    @NotBlank(message = "Chủ đề không thể để trống")
    private String topic;

    @NotNull(message = "Số câu không thể để trống")
    private Integer numberOfQuestions;

    @NotNull(message = "Thời gian làm bài không thể để trống")
    @Positive(message = "Thời gian làm bài phải lớn hơn 0 phút")
    private Integer duration;
}
