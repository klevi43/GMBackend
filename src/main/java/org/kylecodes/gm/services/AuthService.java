package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.UserDto;

public interface AuthService {
    String verify(UserDto userDto);
}
