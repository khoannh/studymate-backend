package exe201.studymatebackend.service;

import exe201.studymatebackend.dto.request.account.CreateAccountRequest;
import exe201.studymatebackend.dto.request.account.UpdateAccountRequest;
import exe201.studymatebackend.dto.response.account.GetAccountResponse;
import exe201.studymatebackend.dto.response.account.GetAllAccountResponse;

import java.util.List;

public interface AccountService {
    List<GetAllAccountResponse> getAllAccount();
    GetAccountResponse getAccountById(Integer id);
    GetAccountResponse createAccount(CreateAccountRequest request);
    GetAccountResponse updateAccount(Integer id, UpdateAccountRequest request);
    void deleteAccount(Integer id);
}
