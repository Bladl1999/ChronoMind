package org.valor.service.user;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.valor.model.dto.ChangePasswordRequest;
import org.valor.model.dto.UpdateProfileRequest;
import org.valor.model.dto.UserProfile;
import org.valor.model.dto.UserSettingsDto;

@Service
public class UserServiceImpl implements UserService{

    @Override
    public UserSettingsDto getSettings(User user) {
        return null;
    }

    @Override
    public UserProfile getProfile(User user) {
        return null;
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
