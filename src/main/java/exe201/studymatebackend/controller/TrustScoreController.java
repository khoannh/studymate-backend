package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.request.account.ChangeTrustScoreRequest;
import exe201.studymatebackend.dto.request.account.UpdateTrustScoreRequest;
import exe201.studymatebackend.dto.response.ApiResponse;
import exe201.studymatebackend.dto.response.account.GetTrustScoreResponse;
import exe201.studymatebackend.dto.response.account.TrustScoreHistoryResponse;
import exe201.studymatebackend.dto.response.account.TrustScoreMutateResponse;
import exe201.studymatebackend.pojo.AccountTrustHistory;
import exe201.studymatebackend.service.TrustScoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrustScoreController {

    private final TrustScoreService trustScoreService;

    // ====== KHỐI THEO accountId (giữ nguyên base path cũ) ======

    @GetMapping("/api/v1/accounts/{accountId}/trust-score")
    public ApiResponse<GetTrustScoreResponse> get(@PathVariable Integer accountId) {
        Integer score = trustScoreService.getScore(accountId);
        GetTrustScoreResponse result = new GetTrustScoreResponse(accountId, score);

        return ApiResponse.<GetTrustScoreResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy điểm tin cậy thành công")
                .result(result)
                .build();
    }

    @PutMapping("/api/v1/accounts/{accountId}/trust-score")
    public ApiResponse<TrustScoreMutateResponse> set(@PathVariable Integer accountId,
                                                     @RequestBody @Valid UpdateTrustScoreRequest req) {
        Integer before = trustScoreService.getScore(accountId);
        Integer after = trustScoreService.setScore(accountId, req.getScore());
        TrustScoreMutateResponse result = new TrustScoreMutateResponse(accountId, before, after, after - before);

        return ApiResponse.<TrustScoreMutateResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật điểm tin cậy thành công")
                .result(result)
                .build();
    }

    @PatchMapping("/api/v1/accounts/{accountId}/trust-score/decrement")
    public ApiResponse<TrustScoreMutateResponse> dec(@PathVariable Integer accountId,
                                                     @RequestBody @Valid ChangeTrustScoreRequest req) {
        Integer before = trustScoreService.getScore(accountId);
        Integer after = trustScoreService.decrement(accountId, req.getBy(), req.getReason());
        TrustScoreMutateResponse result = new TrustScoreMutateResponse(accountId, before, after, after - before);

        return ApiResponse.<TrustScoreMutateResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Giảm điểm tin cậy thành công")
                .result(result)
                .build();
    }

    @PatchMapping("/api/v1/accounts/{accountId}/trust-score/increment")
    public ApiResponse<TrustScoreMutateResponse> inc(@PathVariable Integer accountId,
                                                     @RequestBody @Valid ChangeTrustScoreRequest req) {
        Integer before = trustScoreService.getScore(accountId);
        Integer after = trustScoreService.increment(accountId, req.getBy(), req.getReason());
        TrustScoreMutateResponse result = new TrustScoreMutateResponse(accountId, before, after, after - before);

        return ApiResponse.<TrustScoreMutateResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Tăng điểm tin cậy thành công")
                .result(result)
                .build();
    }

    // 🔹 NEW: Giảm mặc định 5 điểm cho accountId (không cần body)
    @PatchMapping("/api/v1/accounts/{accountId}/trust-score/decrement-default")
    public ApiResponse<TrustScoreMutateResponse> decDefault(@PathVariable Integer accountId) {
        Integer before = trustScoreService.getScore(accountId);
        Integer after = trustScoreService.decrementDefault(accountId);
        TrustScoreMutateResponse result = new TrustScoreMutateResponse(accountId, before, after, after - before);

        return ApiResponse.<TrustScoreMutateResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Giảm mặc định 5 điểm tin cậy thành công")
                .result(result)
                .build();
    }

    @GetMapping("/api/v1/accounts/{accountId}/trust-score/history")
    public ApiResponse<List<TrustScoreHistoryResponse>> history(@PathVariable Integer accountId) {
        List<AccountTrustHistory> list = trustScoreService.history(accountId);
        List<TrustScoreHistoryResponse> result = list.stream()
                .map(h -> new TrustScoreHistoryResponse(h.getDelta(), h.getReason(), h.getCreatedAt()))
                .toList();

        return ApiResponse.<List<TrustScoreHistoryResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy lịch sử điểm tin cậy thành công")
                .result(result)
                .build();
    }

    // ====== NHÓM /current (theo pattern AccountController) ======

    // 🔹 NEW: Lấy trust score của current user
    @GetMapping("/api/v1/accounts/current/trust-score")
    public ApiResponse<GetTrustScoreResponse> getCurrent() {
        Integer score = trustScoreService.getCurrentUserScore();
        // accountId current user chỉ để hiển thị, nếu cần có thể trả null
        GetTrustScoreResponse result = new GetTrustScoreResponse(null, score);

        return ApiResponse.<GetTrustScoreResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy điểm tin cậy người dùng hiện tại thành công")
                .result(result)
                .build();
    }

    // 🔹 NEW: Giảm mặc định 5 điểm cho current user (không cần body)
    @PatchMapping("/api/v1/accounts/current/trust-score/decrement-default")
    public ApiResponse<TrustScoreMutateResponse> decCurrentDefault() {
        Integer before = trustScoreService.getCurrentUserScore();
        Integer after = trustScoreService.decrementCurrentUserDefault();
        TrustScoreMutateResponse result = new TrustScoreMutateResponse(null, before, after, after - before);

        return ApiResponse.<TrustScoreMutateResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Giảm mặc định 5 điểm tin cậy cho người dùng hiện tại thành công")
                .result(result)
                .build();
    }
}
