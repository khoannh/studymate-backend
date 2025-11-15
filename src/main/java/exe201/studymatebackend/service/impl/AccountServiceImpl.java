package exe201.studymatebackend.service.impl;

import exe201.studymatebackend.dto.request.account.RenewPasswordRequest;
import exe201.studymatebackend.dto.request.account.UpdateAccountRequest;
import exe201.studymatebackend.dto.response.account.*;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
                            .coin(account.getCoin())
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
                        .coin(account.getCoin())
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


    // get 1 account by ID
    @Override
    public GetAccountResponse getAccountById(Integer id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
        return mapToGetAccountResponse(account);
    }


    // Update account
    @Override
    @Transactional
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
                .coin(currentUser.getCoin())
                .createdAt(currentUser.getCreatedAt())
                .updatedAt(currentUser.getUpdatedAt())
                .isActive(currentUser.getIsActive())
                .build();
    }



    @Override
    @Transactional
    public UpdateAccountResponse updateCurrentAccount(UpdateAccountRequest request) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer acID = account.getAccountID();

        Account currentUser = accountRepository.findById(acID)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DOES_NOT_EXIST));

        // cập nhật email (hoặc thêm field khác tuỳ nhu cầu)
        currentUser.setEmail(request.getEmail());
        currentUser.setUpdatedAt(LocalDateTime.now());

        accountRepository.save(currentUser);

        return UpdateAccountResponse.builder()
                .accountID(currentUser.getAccountID())
                .email(currentUser.getEmail())
                .build();
    }

    @Override
    @Transactional
    public void renewCurrentPassword(RenewPasswordRequest request) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer acID = account.getAccountID();

        Account currentUser = accountRepository.findById(acID)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DOES_NOT_EXIST));

        // Check old password đúng không
        if (!passwordEncoder.matches(request.getOldPassword(), currentUser.getPassword())) {
            throw new AppException(ErrorCode.OLD_PASSWORD_IS_WRONG);
        }

        // Check confirm password có khớp không
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new AppException(ErrorCode.PASSWORD_DOES_NOT_MATCH);
        }

        // Lưu password mới
        currentUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        currentUser.setUpdatedAt(LocalDateTime.now());

        accountRepository.save(currentUser);
    }
    @Override
    public AvatarResponse uploadAvatar(MultipartFile file) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer acID = account.getAccountID();

        Account currentUser = accountRepository.findById(acID)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DOES_NOT_EXIST));

        try {
            currentUser.setAvatar(file.getBytes());
        } catch (IOException e) {
            throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
        }
        currentUser.setUpdatedAt(LocalDateTime.now());
        accountRepository.save(currentUser);

        return AvatarResponse.builder()
                .accountID(currentUser.getAccountID())
                .message("Avatar uploaded successfully")
                .build();
    }
    @Override
    @Transactional
    public AvatarResponse updateCurrentAvatar(MultipartFile file) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer acID = account.getAccountID();

        Account currentUser = accountRepository.findById(acID)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DOES_NOT_EXIST));

        try {
            currentUser.setAvatar(file.getBytes());
        } catch (IOException e) {
            throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
        }

        currentUser.setUpdatedAt(LocalDateTime.now());
        accountRepository.save(currentUser);

        return AvatarResponse.builder()
                .accountID(currentUser.getAccountID())
                .message("Avatar updated successfully")
                .build();
    }
    @Override
    public byte[] getCurrentUserAvatar() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer acID = account.getAccountID();

        Account currentUser = accountRepository.findById(acID)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DOES_NOT_EXIST));

        if (currentUser.getAvatar() == null) {
            throw new AppException(ErrorCode.FILE_NOT_FOUND);
        }
        return currentUser.getAvatar();
    }


    @Override
    @Transactional
    public void banAccount(String username) {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new AppException(ErrorCode.USER_DOES_NOT_EXIST);
        }

        account.setIsActive(false);
        account.setUpdatedAt(LocalDateTime.now());
        accountRepository.save(account);
    }
    @Override
    @Transactional
    public void unbanAccount(String username) {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new AppException(ErrorCode.USER_DOES_NOT_EXIST);
        }

        if (Boolean.TRUE.equals(account.getIsActive())) {
            throw new AppException(ErrorCode.USER_ALREADY_ACTIVE);
        }

        account.setIsActive(true);
        account.setUpdatedAt(LocalDateTime.now());
        accountRepository.save(account);
    }


    @Override
    public GetCoinResponse getCoin() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return GetCoinResponse.builder()
                .coin(account.getCoin())
                .build();
    }


    // -------------------
    // Helper mapping methods
    private GetAllAccountResponse mapToGetAllAccountResponse(Account account) {
        return GetAllAccountResponse.builder()
                .accountID(account.getAccountID())
                .username(account.getUsername())
                .email(account.getEmail())
                .role(account.getRole())
                .coin(account.getCoin())
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
                .coin(account.getCoin())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .isActive(account.getIsActive())
                .build();
    }
    @Override
    public PublicAccountProfileResponse getPublicProfileByUsername(String username) {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new AppException(ErrorCode.USER_DOES_NOT_EXIST);
        }

        return PublicAccountProfileResponse.builder()
                .accountID(account.getAccountID())
                .username(account.getUsername())
                .email(account.getEmail())
                .trustScore(account.getTrustScore())  // ⭐ Integer
                .build();
    }
    @Override
    public byte[] getAvatarByUsername(String username) {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new AppException(ErrorCode.USER_DOES_NOT_EXIST);
        }

        if (account.getAvatar() == null) {
            throw new AppException(ErrorCode.FILE_NOT_FOUND);
        }

        return account.getAvatar();
    }


}
