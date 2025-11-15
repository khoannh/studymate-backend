package exe201.studymatebackend.service.impl;

import exe201.studymatebackend.exception.AppException;
import exe201.studymatebackend.exception.ErrorCode;
import exe201.studymatebackend.pojo.Account;
import exe201.studymatebackend.pojo.AccountTrustHistory;
import exe201.studymatebackend.repository.AccountRepository;
import exe201.studymatebackend.repository.AccountTrustHistoryRepository;
import exe201.studymatebackend.service.TrustScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TrustScoreServiceImpl implements TrustScoreService {

    private final AccountRepository accountRepository;
    private final AccountTrustHistoryRepository historyRepository;

    private static final int MIN_SCORE = 0;
    private static final int MAX_SCORE = 100;
    private static final int DEFAULT_DECREMENT = 5;

    @Override
    @Transactional(readOnly = true)
    public Integer getScore(Integer accountId) {
        Account acc = accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DOES_NOT_EXIST));
        return acc.getTrustScore();
    }

    @Override
    public Integer setScore(Integer accountId, Integer score) {
        if (score == null) {
            // nếu bạn có ErrorCode.BAD_REQUEST thì dùng cái đó, tạm giữ nguyên để khớp enum của bạn
            throw new AppException(ErrorCode.USER_DOES_NOT_EXIST);
        }
        score = clamp(score);
        Account acc = accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DOES_NOT_EXIST));
        int delta = score - acc.getTrustScore();
        acc.setTrustScore(score);
        accountRepository.save(acc);
        if (delta != 0) saveHistory(acc, delta, "setScore");
        return acc.getTrustScore();
    }

    @Override
    public Integer increment(Integer accountId, Integer by, String reason) {
        if (by == null || by <= 0) by = 1;
        Account acc = accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DOES_NOT_EXIST));
        int newScore = clamp(acc.getTrustScore() + by);
        int delta = newScore - acc.getTrustScore();
        acc.setTrustScore(newScore);
        accountRepository.save(acc);
        if (delta != 0) saveHistory(acc, delta, reason == null ? "increment" : reason);
        return acc.getTrustScore();
    }

    @Override
    public Integer decrement(Integer accountId, Integer by, String reason) {
        if (by == null || by <= 0) by = 1;
        Account acc = accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DOES_NOT_EXIST));
        int newScore = clamp(acc.getTrustScore() - by);
        int delta = newScore - acc.getTrustScore(); // âm
        acc.setTrustScore(newScore);
        accountRepository.save(acc);
        if (delta != 0) saveHistory(acc, delta, reason == null ? "decrement" : reason);
        return acc.getTrustScore();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountTrustHistory> history(Integer accountId) {
        return historyRepository.findByAccount_AccountIDOrderByCreatedAtDesc(accountId);
    }

    // ====== NEW: current user & default -5 ======

    @Override
    @Transactional(readOnly = true)
    public Integer getCurrentUserScore() {
        Account current = getCurrentUser();
        return current.getTrustScore();
    }

    @Override
    public Integer decrementDefault(Integer accountId) {
        return decrement(accountId, DEFAULT_DECREMENT, "decrement_default_5");
    }

    @Override
    public Integer decrementCurrentUserDefault() {
        Account current = getCurrentUser();
        return decrement(current.getAccountID(), DEFAULT_DECREMENT, "decrement_default_5");
    }

    // ====== helpers ======

    private Account getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof Account)) {
            throw new AppException(ErrorCode.USER_DOES_NOT_EXIST);
        }
        Integer id = ((Account) principal).getAccountID();
        return accountRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DOES_NOT_EXIST));
    }

    private int clamp(int value) {
        return Math.max(MIN_SCORE, Math.min(MAX_SCORE, value));
    }

    private void saveHistory(Account acc, int delta, String reason) {
        AccountTrustHistory h = new AccountTrustHistory();
        h.setAccount(acc);
        h.setDelta(delta);
        h.setReason(reason);
        h.setCreatedAt(LocalDateTime.now());
        historyRepository.save(h);
    }
}
