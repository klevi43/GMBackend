package org.kylecodes.gm.mappers.singleEntityMappers;

import org.kylecodes.gm.dtos.RegisterDto;
import org.kylecodes.gm.entities.User;

public class UserToRegisterDtoMapper implements EntityToDtoMapper<User, RegisterDto> {
    @Override
    public RegisterDto mapToDto(User user) {
        RegisterDto userDto = new RegisterDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getUsername());
        userDto.setRole(user.getRole());

        return userDto;
    }
}
