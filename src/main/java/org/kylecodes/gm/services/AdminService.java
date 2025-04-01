package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.UserDto;

import java.util.List;

public interface AdminService {

    List<UserDto> getAllUsers();
}
