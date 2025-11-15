package exe201.studymatebackend.service;

import exe201.studymatebackend.pojo.AccountTrustHistory;

import java.util.List;

public interface TrustScoreService {
    Integer getScore(Integer accountId);
    Integer setScore(Integer accountId, Integer score);
    Integer increment(Integer accountId, Integer by, String reason);
    Integer decrement(Integer accountId, Integer by, String reason);
    List<AccountTrustHistory> history(Integer accountId);

    // ➕ Thêm mới
    Integer getCurrentUserScore();
    Integer decrementDefault(Integer accountId);        // mặc định -5 cho accountId
    Integer decrementCurrentUserDefault();              // mặc định -5 cho current user
}