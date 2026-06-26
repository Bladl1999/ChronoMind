package org.valor.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.valor.model.dto.UsersDto;

import java.time.Instant;

@Entity
@Table(name = "users")
public class Users extends BaseEntity {

    @Column(name = "login", unique = true)
    private String login;
    @Column(name = "password")
    private String password;

    public Users() {
    }

    public Users(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void update(UsersDto usersDto, PasswordEncoder passwordEncoder) {
        this.login = usersDto.login().orElse(login);
        this.password = usersDto.password().orElse(null) != null ? passwordEncoder.encode(usersDto.password().get()) : password;
        setUpdateTimestamp(Instant.now());
    }
}
