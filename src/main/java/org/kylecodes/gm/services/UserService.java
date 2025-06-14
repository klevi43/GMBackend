package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.EmailDto;
import org.kylecodes.gm.dtos.PasswordDto;
import org.kylecodes.gm.dtos.RegisterDto;
import org.kylecodes.gm.dtos.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto registerNewUser(RegisterDto userDto);
    UserDto getUserInfo();
    void updateUserEmail(EmailDto emailDto);
    void updateUserPassword(PasswordDto passwordDto);
    void deleteUser();
}
