package exe201.studymatebackend.service;

import exe201.studymatebackend.dto.request.account.CreateAccountRequest;
import exe201.studymatebackend.dto.request.account.RenewPasswordRequest;
import exe201.studymatebackend.dto.request.account.UpdateAccountRequest;
import exe201.studymatebackend.dto.response.account.*;

import java.util.List;

public interface AccountService {
    List<GetAllAccountResponse> getAllAccount();
    GetAccountResponse getAccountById(Integer id);

    UpdateAccountResponse updateAccount(UpdateAccountRequest request);

    ViewAccountResponse viewCurrentAccount();
    RenewPasswordResponse renewPassword(RenewPasswordRequest request);


    void banAccount(Integer id);
}
