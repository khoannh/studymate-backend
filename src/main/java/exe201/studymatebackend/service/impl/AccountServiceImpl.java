package exe201.studymatebackend.service.impl;

import exe201.studymatebackend.dto.response.account.GetAllAccountResponse;
import exe201.studymatebackend.pojo.Account;
import exe201.studymatebackend.repository.AccountRepository;
import exe201.studymatebackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


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
}
