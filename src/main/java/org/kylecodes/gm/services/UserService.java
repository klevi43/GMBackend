package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.UserDto;

public interface UserService {
    UserDto registerNewUser(UserDto userDto);
}
