package exe201.studymatebackend.service;

import exe201.studymatebackend.dto.request.account.RenewPasswordRequest;
import exe201.studymatebackend.dto.request.account.UpdateAccountRequest;
import exe201.studymatebackend.dto.response.account.*;

import java.util.List;

public interface AccountService {
    List<GetAllAccountResponse> getAllAccount();

    GetAccountPageResponse getAccountPage(int page, int size);

    GetAccountResponse getAccountById(Integer id);

    UpdateAccountResponse updateAccount(UpdateAccountRequest request);

    ViewAccountResponse viewCurrentAccount();

    void renewPassword(RenewPasswordRequest request);

    void banAccount(Integer id);

    GetCoinResponse getCoin();
}
