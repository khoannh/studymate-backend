package exe201.studymatebackend.service.impl;

import exe201.studymatebackend.config.JwtUtil;
import exe201.studymatebackend.dto.request.authentication.LoginRequest;
import exe201.studymatebackend.dto.request.authentication.RegisterRequest;
import exe201.studymatebackend.dto.response.authentication.LoginResponse;
import exe201.studymatebackend.dto.response.authentication.RegisterResponse;
import exe201.studymatebackend.enums.Role;
import exe201.studymatebackend.exception.AppException;
import exe201.studymatebackend.exception.ErrorCode;
import exe201.studymatebackend.pojo.Account;
import exe201.studymatebackend.repository.AccountRepository;
import exe201.studymatebackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Account account = (Account) authentication.getPrincipal();
        String jwtToken = jwtUtil.generateToken(account);
        List<String> roles = account.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return LoginResponse.builder()
                .username(account.getUsername())
                .token(jwtToken)
                .role(roles)
                .build();
    }

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {
        if (accountRepository.findByUsername(registerRequest.getUsername()) != null) {
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXIST);
        }
        if (accountRepository.findByEmail(registerRequest.getEmail()) != null) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXIST);
        }
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORD_DOES_NOT_MATCH);
        }
        Account newAccount = new Account();
        newAccount.setUsername(registerRequest.getUsername());
        newAccount.setEmail(registerRequest.getEmail());
        newAccount.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newAccount.setRole(Role.USER);
        newAccount.setToken(0);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setUpdatedAt(LocalDateTime.now());
        newAccount.setIsActive(true);
        accountRepository.save(newAccount);
        return RegisterResponse.builder()
                .username(registerRequest.getUsername())
                .build();

    }
}
