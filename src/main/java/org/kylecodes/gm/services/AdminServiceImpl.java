package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.UserDto;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.mappers.singleEntityMappers.EntityToDtoMapper;
import org.kylecodes.gm.mappers.singleEntityMappers.UserToUserDtoMapper;
import org.kylecodes.gm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;
    EntityToDtoMapper<User, UserDto> userMapper = new UserToUserDtoMapper();
    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = users.stream().map((user) -> userMapper.mapToDto(user)).toList();
        return userDtoList;
    }
}
