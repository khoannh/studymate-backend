package exe201.studymatebackend.service.impl;

import exe201.studymatebackend.dto.request.account.CreateAccountRequest;
import exe201.studymatebackend.dto.request.account.RenewPasswordRequest;
import exe201.studymatebackend.dto.request.account.UpdateAccountRequest;
import exe201.studymatebackend.dto.response.account.*;
import exe201.studymatebackend.dto.response.authentication.RegisterResponse;
import exe201.studymatebackend.enums.Role;
import exe201.studymatebackend.exception.AppException;
import exe201.studymatebackend.exception.ErrorCode;
import exe201.studymatebackend.pojo.Account;
import exe201.studymatebackend.repository.AccountRepository;
import exe201.studymatebackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<GetAllAccountResponse> getAllAccount() {
        List<Account> accountList = accountRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        if (accountList.isEmpty()) {
            throw new RuntimeException("No accounts found");
        } else {
            return accountList.stream()
                    .map( account -> GetAllAccountResponse.builder()
                            .accountID(account.getAccountID())
                            .username(account.getUsername())
                            .email(account.getEmail())
                            .role(account.getRole())
                            .token(account.getToken())
                            .createdAt(account.getCreatedAt())
                            .updatedAt(account.getUpdatedAt())
                            .isActive(account.getIsActive())
                            .build()
                    ).toList();
        }
    }
    // get 1 account by ID
    @Override
    public GetAccountResponse getAccountById(Integer id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
        return mapToGetAccountResponse(account);
    }


    // Update account
    @Override
    public UpdateAccountResponse updateAccount(UpdateAccountRequest request) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer acID = account.getAccountID();
        Account currentUser = accountRepository.findById(acID)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DOES_NOT_EXIST));


        currentUser.setEmail(request.getEmail());
        currentUser.setUpdatedAt(LocalDateTime.now());

        accountRepository.save(currentUser);
        return UpdateAccountResponse.builder()
                .accountID(currentUser.getAccountID())
                .email(currentUser.getEmail()).build();

    }
    @Override
    public ViewAccountResponse viewCurrentAccount() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer acID = account.getAccountID();

        Account currentUser = accountRepository.findById(acID)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DOES_NOT_EXIST));

        return ViewAccountResponse.builder()
                .accountID(currentUser.getAccountID())
                .username(currentUser.getUsername())
                .email(currentUser.getEmail())
                .role(currentUser.getRole())
                .token(currentUser.getToken())
                .createdAt(currentUser.getCreatedAt())
                .updatedAt(currentUser.getUpdatedAt())
                .isActive(currentUser.getIsActive())
                .build();
    }


    @Override
    public RenewPasswordResponse renewPassword(RenewPasswordRequest request) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer acID = account.getAccountID();
        Account currentUser = accountRepository.findById(acID)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DOES_NOT_EXIST));

        currentUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        currentUser.setUpdatedAt(LocalDateTime.now());

        accountRepository.save(currentUser);

        return RenewPasswordResponse.builder()
                .accountID(currentUser.getAccountID())
                .message("Password updated successfully")
                .build();
    }


    @Override
    public void banAccount(Integer id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));

        account.setIsActive(false);
        account.setUpdatedAt(LocalDateTime.now());

        accountRepository.save(account);
    }



    // -------------------
    // Helper mapping methods
    private GetAllAccountResponse mapToGetAllAccountResponse(Account account) {
        return GetAllAccountResponse.builder()
                .accountID(account.getAccountID())
                .username(account.getUsername())
                .email(account.getEmail())
                .role(account.getRole())
                .token(account.getToken())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .isActive(account.getIsActive())
                .build();
    }

    private GetAccountResponse mapToGetAccountResponse(Account account) {
        return GetAccountResponse.builder()
                .accountID(account.getAccountID())
                .username(account.getUsername())
                .email(account.getEmail())
                .role(account.getRole())
                .token(account.getToken())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .isActive(account.getIsActive())
                .build();
    }
}
