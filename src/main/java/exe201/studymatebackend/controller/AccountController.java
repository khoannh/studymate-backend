package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.request.account.CreateAccountRequest;
import exe201.studymatebackend.dto.request.account.UpdateAccountRequest;
import exe201.studymatebackend.dto.response.ApiResponse;
import exe201.studymatebackend.dto.response.account.GetAccountResponse;
import exe201.studymatebackend.dto.response.account.GetAllAccountResponse;
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



    // Update account by username
    @PutMapping("/accounts/{username}")
    public ApiResponse<GetAccountResponse> updateAccount(@PathVariable String username,
                                                         @RequestBody UpdateAccountRequest request,
                                                         HttpServletRequest httpRequest) {
        String currentUsername = (String) httpRequest.getAttribute("username");

        if (!username.equals(currentUsername)) {
            return ApiResponse.<GetAccountResponse>builder()
                    .code(HttpStatus.FORBIDDEN.value())
                    .message("You can only update your own account")
                    .result(null)
                    .build();
        }

        GetAccountResponse updated = accountService.updateAccountByUsername(username, request);
        return ApiResponse.<GetAccountResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Account updated successfully")
                .result(updated)
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

