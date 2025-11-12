package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.request.quiz.GenerateQuizRequest;
import exe201.studymatebackend.dto.response.ApiResponse;
import exe201.studymatebackend.dto.response.quiz.GenerateQuizResponse;
import exe201.studymatebackend.dto.response.quiz.GetAllQuizSetResponse;
import exe201.studymatebackend.dto.response.quiz.GetQuestionsOfQuizResponse;
import exe201.studymatebackend.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz-management")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/{roomID}")
    public ApiResponse<GenerateQuizResponse> generateQuiz(@PathVariable Integer roomID, @Valid @RequestBody GenerateQuizRequest request) {
        GenerateQuizResponse result = quizService.generateQuiz(roomID, request);
        return ApiResponse.<GenerateQuizResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Tạo bài quiz thành công!")
                .result(result)
                .build();
    }

    @GetMapping("/{roomID}/quizset")
    public ApiResponse<List<GetAllQuizSetResponse>> getAllQuizSet(@PathVariable Integer roomID) {
        List<GetAllQuizSetResponse> result = quizService.getAllQuizSet(roomID);
        return ApiResponse.<List<GetAllQuizSetResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách bài quiz thành công")
                .result(result)
                .build();
    }

    @GetMapping("/{quizSetID}/question")
    public ApiResponse<List<GetQuestionsOfQuizResponse>> getAllQuestionsOfQuiz(@PathVariable Integer quizSetID) {
        List<GetQuestionsOfQuizResponse> result = quizService.getAllQuestions(quizSetID);
        return ApiResponse.<List<GetQuestionsOfQuizResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách câu hỏi thành công")
                .result(result)
                .build();
    }
}
