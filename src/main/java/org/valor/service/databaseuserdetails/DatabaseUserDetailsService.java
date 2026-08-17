package org.valor.service.databaseuserdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.valor.repository.UsersRepository;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public DatabaseUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    //TODO Добавить ещё ролей
    public UserDetails loadUserByUsername(String username) {
        return usersRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь " + username + " не найден"));
    }
}
