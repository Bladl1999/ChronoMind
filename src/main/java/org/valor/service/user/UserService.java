package org.valor.service.user;

import org.apache.catalina.User;
import org.valor.model.dto.ChangePasswordRequest;
import org.valor.model.dto.UpdateProfileRequest;
import org.valor.model.dto.UserProfile;
import org.valor.model.dto.UserSettingsDto;
import org.valor.model.entity.Users;

public interface UserService {
    UserSettingsDto getSettings(Users users);

    UserProfile getProfile(User user);

    UserProfile updateProfile(UpdateProfileRequest request, User user);

    void changePassword(ChangePasswordRequest request, User user);

    UserSettingsDto updateSettings(UserSettingsDto settings, User user);
}
