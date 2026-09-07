package org.valor.mapper;

import org.valor.model.dto.UserProfile;
import org.valor.model.entity.Users;


public class UserMapper {
    public static UserProfile toUserProfile(Users user) {
        if (user == null) return null;
        UserProfile profile = new UserProfile();
        profile.setId(user.getId());
        profile.setEmail(user.getEmail());
        profile.setName(user.getUserName());
        profile.setCreatedAt(user.getCreateTimestamp());
        return profile;
    }
}
