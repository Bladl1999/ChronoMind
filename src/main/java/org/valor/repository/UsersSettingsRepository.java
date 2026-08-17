package org.valor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.valor.model.entity.UserSettings;

import java.util.Optional;

@Repository
public interface UsersSettingsRepository extends JpaRepository<UserSettings, Long> {
    Optional<UserSettings> findByUser_UserName(String username);
}
