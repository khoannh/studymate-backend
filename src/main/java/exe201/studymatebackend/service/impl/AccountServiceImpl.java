package exe201.studymatebackend.service.impl;

import exe201.studymatebackend.dto.response.account.GetAccountPageResponse;
import exe201.studymatebackend.dto.response.account.GetAllAccountResponse;
import exe201.studymatebackend.exception.AppException;
import exe201.studymatebackend.exception.ErrorCode;
import exe201.studymatebackend.pojo.Account;
import exe201.studymatebackend.repository.AccountRepository;
import exe201.studymatebackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            throw new AppException(ErrorCode.USER_DOES_NOT_EXIST);
        } else {
            return accountList.stream()
                    .map(account -> GetAllAccountResponse.builder()
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

    @Override
    public GetAccountPageResponse getAccountPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Account> accountPage = accountRepository.findAll(pageable);
        if (accountPage.isEmpty()) {
            throw new AppException(ErrorCode.USER_DOES_NOT_EXIST);
        }
        List<GetAllAccountResponse> content = accountPage.getContent().stream().map(
                account -> GetAllAccountResponse.builder()
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

        return GetAccountPageResponse.builder()
                .content(content)
                .pageNumber(accountPage.getNumber())
                .pageSize(accountPage.getSize())
                .totalPages(accountPage.getTotalPages())
                .totalElements(accountPage.getTotalElements())
                .isLastPage(accountPage.isLast())
                .build();
    }


}
