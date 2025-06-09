package org.kylecodes.gm.services;

import org.kylecodes.gm.constants.Roles;
import org.kylecodes.gm.dtos.PageDto;
import org.kylecodes.gm.dtos.UserDto;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.exceptions.AlreadyAdminException;
import org.kylecodes.gm.exceptions.AlreadyUserException;
import org.kylecodes.gm.exceptions.UserNotFoundException;
import org.kylecodes.gm.mappers.EntityToDtoMapper;
import org.kylecodes.gm.mappers.UserToUserDtoMapper;
import org.kylecodes.gm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;
    EntityToDtoMapper<User, UserDto> userMapper = new UserToUserDtoMapper();
    @Override
    public PageDto<UserDto> getAllUsers(Integer pageNo, Integer pageSize) {
        Page<User> users = userRepository.findAll(PageRequest.of(pageNo, pageSize, Sort.by("role")));
        List<UserDto> userDtos = users.getContent().stream().map((user) -> userMapper.mapToDto(user)).toList();
        System.out.println(userDtos);
        PageDto<UserDto> pageDto = new PageDto<>();
        pageDto.setContent(userDtos);
        pageDto.setPageNo(users.getNumber());
        pageDto.setPageSize(users.getSize());
        pageDto.setTotalPages(users.getTotalPages());
        pageDto.setTotalElements(users.getTotalElements());
        pageDto.setLastPage(users.isLast());
        return pageDto;
    }

    @Override
    public UserDto promoteToAdmin(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        if (user.get().getRole().equals("ROLE_" + Roles.ADMIN)) {
            throw new AlreadyAdminException();
        }

        user.get().setRole("ROLE_" + Roles.ADMIN);
        User savedUser = userRepository.save(user.get());
        return userMapper.mapToDto(savedUser);
    }

    @Override
    public UserDto demoteToUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        if (user.get().getRole().equals("ROLE_" + Roles.USER)) {
            throw new AlreadyUserException();
        }
        user.get().setRole("ROLE_" + Roles.USER);
        User savedUser = userRepository.save(user.get());
        return userMapper.mapToDto(savedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(userId);
    }


}
