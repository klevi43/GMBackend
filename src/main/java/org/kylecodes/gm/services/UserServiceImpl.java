package org.kylecodes.gm.services;

import org.kylecodes.gm.constants.NotFoundMsg;
import org.kylecodes.gm.constants.Roles;
import org.kylecodes.gm.dtos.AuthUserDto;
import org.kylecodes.gm.dtos.RegisterDto;
import org.kylecodes.gm.dtos.UserDto;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.exceptions.AlreadyLoggedInException;
import org.kylecodes.gm.mappers.EntityToDtoMapper;
import org.kylecodes.gm.mappers.UserToUserDtoMapper;
import org.kylecodes.gm.repositories.UserRepository;
import org.kylecodes.gm.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final EntityToDtoMapper<User, UserDto> userMapper = new UserToUserDtoMapper();

    @Override
    public UserDto registerNewUser(RegisterDto registerDto) {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            throw new AlreadyLoggedInException();
        }
        User newUser = new User();

        newUser.setEmail(registerDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        newUser.setRole(Roles.USER);
        User savedUser = userRepository.save(newUser);
        return userMapper.mapToDto(savedUser);
    }

    @Override
    public UserDto getUserInfo() {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        return userMapper.mapToDto(user);
    }

    @Override
    public UserDto updateUserInfo(AuthUserDto updateUser) {
        User currentUser = SecurityUtil.getPrincipalFromSecurityContext();
        if (updateUser.getEmail() != null) {
            currentUser.setEmail(updateUser.getEmail());
        }
        if(updateUser.getPassword() != null) {
            currentUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        }
        User savedUser = userRepository.save(currentUser);
        return userMapper.mapToDto(savedUser);
    }

    @Override
    public void deleteUser() {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        userRepository.delete(user);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + NotFoundMsg.EMAIL_NOT_FOUND_MSG));
        return user;

    }



}
