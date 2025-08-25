package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.response.account.GetAllAccountResponse;
import exe201.studymatebackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<GetAllAccountResponse>> getAllAccounts() {
        List<GetAllAccountResponse> result = accountService.getAllAccount();
        return ResponseEntity.ok(result);
    }
}
