package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.request.authentication.LoginRequest;
import exe201.studymatebackend.dto.response.authentication.LoginResponse;
import exe201.studymatebackend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse result = authService.login(loginRequest);
        return ResponseEntity.ok(result);

    }
}
