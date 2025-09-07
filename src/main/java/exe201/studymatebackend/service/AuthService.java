package exe201.studymatebackend.service;

import exe201.studymatebackend.dto.request.authentication.LoginRequest;
import exe201.studymatebackend.dto.request.authentication.RegisterRequest;
import exe201.studymatebackend.dto.response.authentication.LoginResponse;
import exe201.studymatebackend.dto.response.authentication.RegisterResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    RegisterResponse register(RegisterRequest registerRequest);
}
