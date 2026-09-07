package org.valor.service.user;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.valor.mapper.UserMapper;
import org.valor.mapper.UserSettingsMapper;
import org.valor.model.dto.ChangePasswordRequest;
import org.valor.model.dto.UpdateProfileRequest;
import org.valor.model.dto.UserProfile;
import org.valor.model.dto.UserSettingsDto;
import org.valor.model.entity.Users;
import org.valor.repository.UsersRepository;
import org.valor.repository.UsersSettingsRepository;

@Service
public class UserServiceImpl implements UserService{

    private final UsersSettingsRepository usersSettingsRepository;
    private final UserSettingsMapper userSettingsMapper;
    private final UsersRepository usersRepository;

    @Autowired
    public UserServiceImpl(
            UsersSettingsRepository usersSettingsRepository,
            UserSettingsMapper userSettingsMapper,
            UsersRepository usersRepository
    ) {
        this.usersSettingsRepository = usersSettingsRepository;
        this.userSettingsMapper = userSettingsMapper;
        this.usersRepository = usersRepository;
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
    public UserProfile updateProfile(UpdateProfileRequest request, User user) {
        return null;
    }

    @Override
    public void changePassword(ChangePasswordRequest request, User user) {

    }

    @Override
    public UserSettingsDto updateSettings(UserSettingsDto settings, User user) {
        return null;
    }
}
