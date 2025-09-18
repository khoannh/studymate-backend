package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.request.account.CreateAccountRequest;
import exe201.studymatebackend.dto.request.account.RenewPasswordRequest;
import exe201.studymatebackend.dto.request.account.UpdateAccountRequest;
import exe201.studymatebackend.dto.response.ApiResponse;
import exe201.studymatebackend.dto.response.account.*;
import exe201.studymatebackend.pojo.Account;
import exe201.studymatebackend.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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



    @PutMapping("/accounts/")
    public ApiResponse<UpdateAccountResponse> updateAccount(

            @RequestBody UpdateAccountRequest request) {


        UpdateAccountResponse result = accountService.updateAccount(request);

        return ApiResponse.<UpdateAccountResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Account updated successfully")
                .result(result)
                .build();
    }
    @GetMapping("/accounts/current")
    public ApiResponse<ViewAccountResponse> viewCurrentAccount() {
        ViewAccountResponse result = accountService.viewCurrentAccount();
        return ApiResponse.<ViewAccountResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Retrieved current account successfully")
                .result(result)
                .build();
    }
    // Renew current user's password
    @PutMapping("/accounts/current/renew-password")
    public ApiResponse<RenewPasswordResponse> renewPassword(
            @RequestBody RenewPasswordRequest request) {

        RenewPasswordResponse result = accountService.renewPassword(request);
        return ApiResponse.<RenewPasswordResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Password renewed successfully")
                .result(result)
                .build();
    }




    // Ban account
    @PutMapping("/accounts/{id}/ban")
    public ApiResponse<Void> banAccount(@PathVariable Integer id) {
        accountService.banAccount(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Account banned successfully")
                .build();
    }
}

