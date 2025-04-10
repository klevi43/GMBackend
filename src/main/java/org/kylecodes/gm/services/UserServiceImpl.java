package org.kylecodes.gm.services;

import org.kylecodes.gm.constants.NotFoundMsg;
import org.kylecodes.gm.constants.Roles;
import org.kylecodes.gm.dtos.AuthUserDto;
import org.kylecodes.gm.dtos.RegisterDto;
import org.kylecodes.gm.dtos.UserDto;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.exceptions.AlreadyLoggedInException;
import org.kylecodes.gm.exceptions.EmailAlreadyExistsException;
import org.kylecodes.gm.mappers.singleEntityMappers.EntityToDtoMapper;
import org.kylecodes.gm.mappers.singleEntityMappers.UserToUserDtoMapper;
import org.kylecodes.gm.repositories.UserRepository;
import org.kylecodes.gm.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final EntityToDtoMapper<User, UserDto> userMapper = new UserToUserDtoMapper();

    @Override
    public UserDto registerNewUser(RegisterDto registerDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            throw new AlreadyLoggedInException();
        }
        registerDto.setEmail(registerDto.getEmail().trim());
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new EmailAlreadyExistsException();
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
            if (userRepository.existsByEmail(updateUser.getEmail())) {
                throw new EmailAlreadyExistsException();
            }
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
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + NotFoundMsg.EMAIL_NOT_FOUND_MSG));

    }



}
