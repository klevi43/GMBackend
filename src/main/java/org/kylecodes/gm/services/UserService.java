package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.AuthUserDto;
import org.kylecodes.gm.dtos.RegisterDto;
import org.kylecodes.gm.dtos.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto registerNewUser(RegisterDto userDto);
    UserDto getUserInfo();
    UserDto updateUserInfo(AuthUserDto authUserDto);
    void deleteUser();
}
