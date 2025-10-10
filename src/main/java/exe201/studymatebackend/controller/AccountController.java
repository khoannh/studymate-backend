package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.request.account.RenewPasswordRequest;
import exe201.studymatebackend.dto.request.account.UpdateAccountRequest;
import exe201.studymatebackend.dto.response.ApiResponse;
import exe201.studymatebackend.dto.response.account.*;
import exe201.studymatebackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account-management")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    public ApiResponse<Void> renewPassword(
            @RequestBody RenewPasswordRequest request) {

        accountService.renewPassword(request);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Password renewed successfully")
                .build();
    }


    // Ban account
    @PutMapping("/accounts/{id}/ban")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<Void> banAccount(@PathVariable Integer id) {
        accountService.banAccount(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Account banned successfully")
                .build();
    }

    @GetMapping("/account")
    public ApiResponse<GetAccountPageResponse> getAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        GetAccountPageResponse result = accountService.getAccountPage(page, size);
        return ApiResponse.<GetAccountPageResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Retrieved all accounts successfully")
                .result(result)
                .build();
    }

    @GetMapping("/accounts/current/coin")
    public ApiResponse<GetCoinResponse> getCoin() {
        GetCoinResponse result = accountService.getCoin();
        return ApiResponse.<GetCoinResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Retrieved coin successfully")
                .result(result)
                .build();
    }
}
