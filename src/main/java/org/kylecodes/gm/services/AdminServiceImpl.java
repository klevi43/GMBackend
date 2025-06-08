package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.PageDto;
import org.kylecodes.gm.dtos.UserDto;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.mappers.EntityToDtoMapper;
import org.kylecodes.gm.mappers.UserToUserDtoMapper;
import org.kylecodes.gm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;
    EntityToDtoMapper<User, UserDto> userMapper = new UserToUserDtoMapper();
    @Override
    public PageDto<UserDto> getAllUsers(Integer pageNo, Integer pageSize) {
        Page<User> users = userRepository.findAll(PageRequest.of(pageNo, pageSize, Sort.by("role")));
        List<UserDto> userDtos = users.getContent().stream().map((user) -> userMapper.mapToDto(user)).toList();

        PageDto<UserDto> pageDto = new PageDto<>();
        pageDto.setContent(userDtos);
        pageDto.setPageNo(users.getNumber());
        pageDto.setPageSize(users.getSize());
        pageDto.setTotalPages(users.getTotalPages());
        pageDto.setTotalElements(users.getTotalElements());
        pageDto.setLastPage(users.isLast());
        return pageDto;
    }
}
