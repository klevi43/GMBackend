package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.AuthUserDto;
import org.kylecodes.gm.dtos.JwtDto;

public interface AuthService {
    JwtDto verify(AuthUserDto authUserDto);
}
