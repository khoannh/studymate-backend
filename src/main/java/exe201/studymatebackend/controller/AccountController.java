package exe201.studymatebackend.controller;


import exe201.studymatebackend.dto.request.account.RenewPasswordRequest;
import exe201.studymatebackend.dto.request.account.UpdateAccountRequest;
import exe201.studymatebackend.dto.response.ApiResponse;
import exe201.studymatebackend.dto.response.account.*;
import exe201.studymatebackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    // Upload avatar
    @PostMapping(value = "/accounts/current/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<AvatarResponse> uploadAvatar(@RequestParam("file") MultipartFile file) {
        AvatarResponse result = accountService.uploadAvatar(file);
        return ApiResponse.<AvatarResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Avatar uploaded successfully")
                .result(result)
                .build();
    }

    // Get avatar
    @GetMapping("/accounts/current/avatar")
    public ResponseEntity<byte[]> getCurrentUserAvatar() {
        byte[] image = accountService.getCurrentUserAvatar();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"avatar.png\"")
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
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

