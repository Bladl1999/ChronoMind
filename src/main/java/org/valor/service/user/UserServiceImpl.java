package org.valor.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.valor.mapper.UserMapper;
import org.valor.mapper.UserSettingsMapper;
import org.valor.model.dto.ChangePasswordRequest;
import org.valor.model.dto.UpdateProfileRequest;
import org.valor.model.dto.UserProfile;
import org.valor.model.dto.UserSettingsDto;
import org.valor.model.entity.UserSettings;
import org.valor.model.entity.Users;
import org.valor.repository.UsersRepository;
import org.valor.repository.UsersSettingsRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UsersSettingsRepository usersSettingsRepository;
    private final UserSettingsMapper userSettingsMapper;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
            UsersSettingsRepository usersSettingsRepository,
            UserSettingsMapper userSettingsMapper,
            UsersRepository usersRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usersSettingsRepository = usersSettingsRepository;
        this.userSettingsMapper = userSettingsMapper;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserSettingsDto getSettings(Users users) {
        return userSettingsMapper.toDto(usersSettingsRepository.findByUser_UserName(users.getUsername()).orElseThrow(() -> new RuntimeException("Настройки не найдены для пользователя " + users.getUserName())));

    }

    @Override
    public UserProfile getProfile(Users user) {
        return UserMapper.toUserProfile(usersRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("Не найден профиль")));
    }

    @Override
    @Transactional
    public UserProfile updateProfile(UpdateProfileRequest request, Users user) {
        Users getUser = usersRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("Пользователь не нацйден"));
        getUser.updateProfile(request);
        return UserMapper.toUserProfile(user);
    }

    @Override
    public void changePassword(ChangePasswordRequest request, Users user) {

    }

    @Override
    @Transactional
    public UserSettingsDto updateSettings(UserSettingsDto settings, Users user) {
        UserSettings userSettings = usersSettingsRepository.findById(user.getSettings().getId()).orElseThrow(() -> new RuntimeException("Настройки не найдены"));
        userSettings.update(settings);
        return settings;
    }
}
