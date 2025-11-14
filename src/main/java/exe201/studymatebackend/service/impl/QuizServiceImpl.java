package exe201.studymatebackend.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import exe201.studymatebackend.dto.request.quiz.GenerateQuizRequest;
import exe201.studymatebackend.dto.response.quiz.GenerateQuizResponse;
import exe201.studymatebackend.dto.response.quiz.GetAllQuizSetResponse;
import exe201.studymatebackend.dto.response.quiz.GetQuestionsOfQuizResponse;
import exe201.studymatebackend.exception.AppException;
import exe201.studymatebackend.exception.ErrorCode;
import exe201.studymatebackend.pojo.*;
import exe201.studymatebackend.repository.*;
import exe201.studymatebackend.service.GeminiService;
import exe201.studymatebackend.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AccountRoomRepository accountRoomRepository;

    @Autowired
    private QuizSetRepository quizSetRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private GeminiService geminiService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    @Transactional
    public GenerateQuizResponse generateQuiz(Integer roomID, GenerateQuizRequest request) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer accountID = account.getAccountID();
        Account quizCreator = accountRepository.findByAccountID(accountID);
        Room room = roomRepository.findByRoomID(roomID);
        System.out.println(roomID);
        if (room == null) {
            throw new AppException(ErrorCode.ROOM_DOES_NOT_EXIST);
        }
        String quizJson = geminiService.generateQuizContent(request.getTopic(), request.getNumberOfQuestions());
        System.out.println(quizJson);
        try {
            // ✅ Parse JSON kết quả từ Gemini
            List<Map<String, Object>> quizData = objectMapper.readValue(quizJson, new TypeReference<>() {
            });

            // ✅ Tạo QuizSet
            QuizSet quizSet = new QuizSet();
            quizSet.setQuizSetName(request.getQuizSetName());
            System.out.println(request.getQuizSetName());
            quizSet.setCreatedBy(quizCreator.getUsername());
            quizSet.setTopic(request.getTopic());
            quizSet.setCreatedAt(LocalDateTime.now());
            quizSet.setExpiredAt(LocalDateTime.now().plusDays(7));
            quizSet.setDuration(request.getDuration());
            quizSet.setTotalQuestions(request.getNumberOfQuestions());
            quizSet.setRoom(room);
            quizSetRepository.save(quizSet);

            // ✅ Tạo danh sách Question
            List<Question> questionsToSave = new ArrayList<>();
            for (Map<String, Object> item : quizData) {
                Question question = new Question();
                question.setQuestion((String) item.get("question"));
                List<String> options = (List<String>) item.get("options");
                question.setOptions(objectMapper.writeValueAsString(options));
                question.setAnswer((String) item.get("answer"));
                question.setQuizset(quizSet);
                questionsToSave.add(question);
            }

            // ✅ Chỉ gọi DB 1 lần để lưu tất cả
            questionRepository.saveAll(questionsToSave);

            // ✅ Chuẩn bị response
            List<GenerateQuizResponse.QuestionResponse> questions = quizData.stream()
                    .map(q -> new GenerateQuizResponse.QuestionResponse(
                            (String) q.get("question"),
                            (List<String>) q.get("options"),
                            (String) q.get("answer")
                    )).collect(Collectors.toList());

            return GenerateQuizResponse.builder()
                    .quizSetID(quizSet.getQuizSetID())
                    .quizSetName(quizSet.getQuizSetName())
                    .createdBy(quizSet.getCreatedBy())
                    .createdAt(quizSet.getCreatedAt())
                    .duration(quizSet.getDuration())
                    .topic(quizSet.getTopic())
                    .expiredAt(quizSet.getExpiredAt())
                    .numberOfQuestions(questions.size())
                    .questions(questions)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse quiz from Gemini: " + e.getMessage(), e);
        }
    }

    @Override
    public List<GetAllQuizSetResponse> getAllQuizSet(Integer roomID) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer accountID = account.getAccountID();
        Account currentAccount = accountRepository.findByAccountID(accountID);
        Room room = roomRepository.findByRoomID(roomID);
        System.out.println(roomID);
        if (room == null) {
            throw new AppException(ErrorCode.ROOM_DOES_NOT_EXIST);
        }

        Optional<AccountRoom> accountRoomOpt = accountRoomRepository.findByAccountAndRoom(currentAccount, room);
        if (accountRoomOpt.isEmpty()) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_IN_ROOM);
        }
        List<QuizSet> quizSetList = quizSetRepository.findAllByRoomOrderByCreatedAtDesc(room);
        return quizSetList.stream().map(quizSet -> GetAllQuizSetResponse.builder()
                .quizSetID(quizSet.getQuizSetID())
                .quizSetName(quizSet.getQuizSetName())
                .createdBy(quizSet.getCreatedBy())
                .createdAt(quizSet.getCreatedAt())
                .expiredAt(quizSet.getExpiredAt())
                .duration(quizSet.getDuration())
                .topic(quizSet.getTopic())
                .numberOfQuestions(quizSet.getTotalQuestions())
                .build()).toList();
    }

    @Override
    public List<GetQuestionsOfQuizResponse> getAllQuestions(Integer quizSetID) {
        QuizSet quizSet = quizSetRepository.findByQuizSetID(quizSetID);
        if (quizSet == null) {
            throw new AppException(ErrorCode.QUIZSET_NOT_FOUND);
        }
        List<Question> questionList = questionRepository.findAllByQuizset(quizSet);
        return questionList.stream().map(question -> GetQuestionsOfQuizResponse.builder()
                .questionID(question.getQuestionID())
                .question(question.getQuestion())
                .options(question.getOptions())
                .answer(question.getAnswer())
                .build()).toList();
    }

    // 🧹 Xóa quiz hết hạn mỗi ngày (chạy 1 lần/ngày lúc 3h sáng)
    @Scheduled(cron = "0 0 3 * * ?")
    @Transactional
    public void deleteExpiredQuizzes() {
        List<QuizSet> expiredQuizzes = quizSetRepository.findAllByExpiredAtBefore(LocalDateTime.now());
        if (!expiredQuizzes.isEmpty()) {
            quizSetRepository.deleteAll(expiredQuizzes);
        }
    }
}
