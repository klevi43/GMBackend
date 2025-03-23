package org.kylecodes.gm.services;

import org.kylecodes.gm.constants.NotFoundMsg;
import org.kylecodes.gm.constants.Roles;
import org.kylecodes.gm.dtos.UserDto;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.repositories.UserRepository;
import org.kylecodes.gm.services.mappers.EntityToDtoMapper;
import org.kylecodes.gm.services.mappers.UserToUserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserDto registerNewUser(UserDto userDto) {
        User newUser = new User();
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRole(Roles.USER);
        User savedUser = userRepository.save(newUser);
        return userMapper.mapToDto(savedUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + NotFoundMsg.EMAIL_NOT_FOUND_MSG));
        return user;
    }

}
