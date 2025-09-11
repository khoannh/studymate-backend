package exe201.studymatebackend.service.impl;

import exe201.studymatebackend.dto.request.account.CreateAccountRequest;
import exe201.studymatebackend.dto.request.account.UpdateAccountRequest;
import exe201.studymatebackend.dto.response.account.GetAccountResponse;
import exe201.studymatebackend.dto.response.account.GetAllAccountResponse;
import exe201.studymatebackend.enums.Role;
import exe201.studymatebackend.pojo.Account;
import exe201.studymatebackend.repository.AccountRepository;
import exe201.studymatebackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

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

    // Create account
    @Override
    public GetAccountResponse createAccount(CreateAccountRequest request) {
        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setEmail(request.getEmail());
        account.setPassword(request.getPassword()); // lưu ý: thường phải encode password
        account.setIsActive(true);
        account.setRole(Role.USER);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());

        Account saved = accountRepository.save(account);
        return mapToGetAccountResponse(saved);
    }

    // Update account
    @Override
    public GetAccountResponse updateAccount(Integer id, UpdateAccountRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));

        if (request.getEmail() != null) account.setEmail(request.getEmail());
        if (request.getPassword() != null) account.setPassword(request.getPassword()); // nhớ encode nếu có Spring Security
        account.setUpdatedAt(LocalDateTime.now());

        Account updated = accountRepository.save(account);
        return mapToGetAccountResponse(updated);
    }

    // Delete account
    @Override
    public void deleteAccount(Integer id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
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
