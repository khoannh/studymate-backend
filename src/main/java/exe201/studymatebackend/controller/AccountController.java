package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.response.ApiResponse;
import exe201.studymatebackend.dto.response.account.GetAllAccountResponse;
import exe201.studymatebackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                .resutl(result)
                .build();
    }
}
