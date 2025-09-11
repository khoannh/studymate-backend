package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.request.account.CreateAccountRequest;
import exe201.studymatebackend.dto.request.account.UpdateAccountRequest;
import exe201.studymatebackend.dto.response.ApiResponse;
import exe201.studymatebackend.dto.response.account.GetAccountResponse;
import exe201.studymatebackend.dto.response.account.GetAllAccountResponse;
import exe201.studymatebackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account-management")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    public ApiResponse<List<GetAllAccountResponse>> getAllAccounts() {
        List<GetAllAccountResponse> result = accountService.getAllAccount();
        return ApiResponse.<List<GetAllAccountResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Retrieved all accounts successfully")
                .result(result)
                .build();
    }
    // Get one account by id
    @GetMapping("/accounts/{id}")
    public ApiResponse<GetAccountResponse> getAccountById(@PathVariable Integer id) {
        GetAccountResponse account = accountService.getAccountById(id);
        return ApiResponse.<GetAccountResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Retrieved account successfully")
                .result(account)
                .build();
    }

    // Create new account
    @PostMapping("/accounts")
    public ApiResponse<GetAccountResponse> createAccount(@RequestBody CreateAccountRequest request) {
        GetAccountResponse created = accountService.createAccount(request);
        return ApiResponse.<GetAccountResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Account created successfully")
                .result(created)
                .build();
    }

    // Update account
    @PutMapping("/accounts/{id}")
    public ApiResponse<GetAccountResponse> updateAccount(@PathVariable Integer id,
                                                         @RequestBody UpdateAccountRequest request) {
        GetAccountResponse updated = accountService.updateAccount(id, request);
        return ApiResponse.<GetAccountResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Account updated successfully")
                .result(updated)
                .build();
    }

    // Delete account
    @DeleteMapping("/accounts/{id}")
    public ApiResponse<Void> deleteAccount(@PathVariable Integer id) {
        accountService.deleteAccount(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Account deleted successfully")
                .build();
    }
}

