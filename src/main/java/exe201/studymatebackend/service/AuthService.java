package exe201.studymatebackend.service;

import exe201.studymatebackend.dto.request.authentication.LoginRequest;
import exe201.studymatebackend.dto.response.authentication.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}
