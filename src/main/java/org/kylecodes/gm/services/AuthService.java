package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.AuthUserDto;

public interface AuthService {
    String verify(AuthUserDto authUserDto);
}
