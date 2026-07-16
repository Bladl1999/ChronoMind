package org.valor.service.auth;

import org.springframework.http.ResponseEntity;
import org.valor.model.dto.AuthResponse;
import org.valor.model.dto.AuthorRequest;

public interface AuthService {
    void logout(String token);

    ResponseEntity<AuthResponse> login(AuthorRequest authorRequest);
}
