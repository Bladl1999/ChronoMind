package org.valor.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.valor.model.dto.UsersDto;
import org.valor.model.entity.Users;
import org.valor.repository.UsersRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {

    private final UsersRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(
            UsersRepository repository,
            PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UsersDto> getAllUsers(String date, Pageable pageable) {

        return repository.findAll(pageable)
                .map(UsersDto::fromEntity)
                .toList();
    }

    @Override
    public UsersDto getById(UUID id) {
        Users users = repository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найдена"));
        return UsersDto.fromEntity(users);
    }

    @Override
    public UUID createUsers(UsersDto usersDto) {
        Users user = new Users();
        user.setLogin(usersDto.login().orElseThrow(() -> new RuntimeException("Отсутствует логин")));
        user.setPassword(passwordEncoder.encode(usersDto.password().orElseThrow(() -> new RuntimeException("Отсутствует пароль"))));

        repository.save(user);
        return user.getId();
    }

    @Override
    public UUID updateUsers(UUID id, UsersDto usersDto) {
        Users getUser = repository.findById(id).orElseThrow(() -> new RuntimeException("Отсутствует пользователь"));
        getUser.update(usersDto, passwordEncoder);
        return getUser.getId();
    }

    @Override
    public UUID deleteUsers(UUID id) {
        Users getUser = repository.findById(id).orElseThrow(() -> new RuntimeException("Отсутствует пользователь"));
        Instant deleteTimestamp = Instant.now();
        getUser.setDeleted(true);
        getUser.setUpdateTimestamp(deleteTimestamp);
        getUser.setLogin(String.format("[ %s %s]", getUser.getLogin(), deleteTimestamp.toString()));
        return getUser.getId();
    }
}
