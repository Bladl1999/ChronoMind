package org.valor.controller;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.valor.model.dto.ChangePasswordRequest;
import org.valor.model.dto.UpdateProfileRequest;
import org.valor.model.dto.UserProfile;
import org.valor.model.dto.UserSettingsDto;
import org.valor.service.user.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserProfile getProfile(@AuthenticationPrincipal User user) {
        return userService.getProfile(user);
    }

    @PutMapping("/me")
    public UserProfile updateProfile(@RequestBody UpdateProfileRequest request,
                                     @AuthenticationPrincipal User user) {
        return userService.updateProfile(request, user);
    }

    @PutMapping("/me/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@RequestBody ChangePasswordRequest request,
                               @AuthenticationPrincipal User user) {
        userService.changePassword(request, user);
    }

    @GetMapping("/settings")
    public UserSettingsDto getSettings(@AuthenticationPrincipal User user) {
        return userService.getSettings(user);
    }

    @PutMapping("/settings")
    public UserSettingsDto updateSettings(@RequestBody UserSettingsDto settings,
                                          @AuthenticationPrincipal User user) {
        return userService.updateSettings(settings, user);
    }
}
