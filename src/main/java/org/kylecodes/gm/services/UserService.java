package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.RegisterDto;

public interface UserService {
    RegisterDto registerNewUser(RegisterDto userDto);
}
