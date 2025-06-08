package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.PageDto;
import org.kylecodes.gm.dtos.UserDto;

public interface AdminService {

    PageDto<UserDto> getAllUsers(Integer pageNo, Integer pageSize);
    UserDto promoteToAdmin(Long userId);
    void deleteUser(Long userId);
}
