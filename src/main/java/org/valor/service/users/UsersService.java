package org.valor.service.users;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.valor.model.dto.UsersDto;

import java.util.List;
import java.util.UUID;

@Service
public interface UsersService {
    List<UsersDto> getAllUsers(String date, Pageable pageable);
    UsersDto getById(UUID id);
    UUID createUsers(UsersDto usersDto);
    UUID updateUsers(UUID id, UsersDto usersDto);
    UUID deleteUsers(UUID id);

}
