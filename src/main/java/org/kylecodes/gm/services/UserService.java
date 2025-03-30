package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.RegisterDto;
import org.kylecodes.gm.dtos.UserDto;

public interface UserService {
    RegisterDto registerNewUser(RegisterDto userDto);
    UserDto getUserInfo();
    void deleteUser();
}
