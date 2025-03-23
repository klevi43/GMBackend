package org.kylecodes.gm.services.mappers;

import org.kylecodes.gm.dtos.RegisterDto;
import org.kylecodes.gm.entities.User;

public class UserToUserDtoMapper implements EntityToDtoMapper<User, RegisterDto> {
    @Override
    public RegisterDto mapToDto(User user) {
        RegisterDto userDto = new RegisterDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());

        return userDto;
    }
}
