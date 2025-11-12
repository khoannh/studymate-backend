package exe201.studymatebackend.service;

import exe201.studymatebackend.dto.request.quiz.GenerateQuizRequest;
import exe201.studymatebackend.dto.response.quiz.GenerateQuizResponse;
import exe201.studymatebackend.dto.response.quiz.GetAllQuizSetResponse;
import exe201.studymatebackend.dto.response.quiz.GetQuestionsOfQuizResponse;

import java.util.List;

public interface QuizService {
    GenerateQuizResponse generateQuiz(Integer roomID, GenerateQuizRequest request);

    List<GetAllQuizSetResponse> getAllQuizSet(Integer roomID);

    List<GetQuestionsOfQuizResponse> getAllQuestions(Integer quizSetID);
}
