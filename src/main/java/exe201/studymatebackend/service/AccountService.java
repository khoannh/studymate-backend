package exe201.studymatebackend.service;

import exe201.studymatebackend.dto.response.account.GetAccountPageResponse;
import exe201.studymatebackend.dto.response.account.GetAllAccountResponse;

import java.util.List;

public interface AccountService {
    List<GetAllAccountResponse> getAllAccount();

    GetAccountPageResponse getAccountPage(int page, int size);
}
