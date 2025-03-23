package org.kylecodes.gm.services.mappers;

import org.kylecodes.gm.dtos.UserDto;
import org.kylecodes.gm.entities.User;

public class UserToUserDtoMapper implements EntityToDtoMapper<User, UserDto> {
    @Override
    public UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getUsername());
        userDto.setRole(user.getRole());

        return userDto;
    }
}
