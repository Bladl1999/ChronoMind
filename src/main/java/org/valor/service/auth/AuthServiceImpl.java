package org.valor.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.valor.model.dto.AuthResponse;
import org.valor.model.dto.AuthorRequest;
import org.valor.model.dto.RegisterRequest;
import org.valor.model.entity.Users;
import org.valor.repository.UsersRepository;
import org.valor.utils.RsaJwtUtils;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final RsaJwtUtils jwtUtils;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            RsaJwtUtils jwtUtils,
            UsersRepository usersRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public ResponseEntity<AuthResponse> login(AuthorRequest authorRequest) {
//        Users users = usersRepository.findByEmail(authorRequest.email()).orElseThrow(() -> new RuntimeException("Не верный логин или пароль"));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authorRequest.name(), authorRequest.password())
        );

        // Генерация JWT (с подписью приватным RSA-ключом)
        String token = jwtUtils.generateToken(authentication.getName());

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @Override
    public void logout(String token) {

    }

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        Users users = new Users();
        users.setUserName(request.name());
        users.setPasswordHash(passwordEncoder.encode(request.password())); // хешируем
        users.setEmail(request.email());
        usersRepository.save(users);
    }
}
