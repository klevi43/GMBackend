package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.AuthUserDto;
import org.kylecodes.gm.dtos.RegisterDto;
import org.kylecodes.gm.dtos.UserDto;

public interface UserService {
    UserDto registerNewUser(RegisterDto userDto);
    UserDto getUserInfo();
    UserDto updateUserInfo(AuthUserDto authUserDto);
    void deleteUser();
}
