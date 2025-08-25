package exe201.studymatebackend.service.impl;

import exe201.studymatebackend.config.JwtUtil;
import exe201.studymatebackend.dto.request.authentication.LoginRequest;
import exe201.studymatebackend.dto.response.authentication.LoginResponse;
import exe201.studymatebackend.pojo.Account;
import exe201.studymatebackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

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
}
