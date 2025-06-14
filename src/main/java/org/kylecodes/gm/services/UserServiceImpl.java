package org.kylecodes.gm.services;

import org.kylecodes.gm.constants.InvalidCredentials;
import org.kylecodes.gm.constants.Roles;
import org.kylecodes.gm.dtos.EmailDto;
import org.kylecodes.gm.dtos.PasswordDto;
import org.kylecodes.gm.dtos.RegisterDto;
import org.kylecodes.gm.dtos.UserDto;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.exceptions.*;
import org.kylecodes.gm.mappers.EntityToDtoMapper;
import org.kylecodes.gm.mappers.UserToUserDtoMapper;
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
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            throw new PasswordAndConfirmPasswordNotEqualException();
        }
        User user = new User();

        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole("ROLE_" + Roles.USER);
        User savedUser = userRepository.save(user);
        return userMapper.mapToDto(savedUser);
    }

    @Override
    public UserDto getUserInfo() {
        User userDetailsModel = SecurityUtil.getPrincipalFromSecurityContext();
        return userMapper.mapToDto(userDetailsModel);
    }

    @Override
    public void updateUserEmail(EmailDto emailDto) {
        User currentUser = SecurityUtil.getPrincipalFromSecurityContext();
        if (!passwordEncoder.matches(emailDto.getPassword(), currentUser.getPassword())) {
            throw new InvalidPasswordException();
        }

        if (!emailDto.getCurrentEmail().equals(currentUser.getUsername())) {
            throw new InvalidEmailException();
        }

        if (emailDto.getCurrentEmail().equals(emailDto.getNewEmail())) {
            throw new NewEmailMatchesCurrentEmailException();
        }

        if (userRepository.existsByEmail(emailDto.getNewEmail())) {
            throw new EmailAlreadyExistsException();
        }

        currentUser.setEmail(emailDto.getNewEmail());
        userRepository.save(currentUser);
    }

    @Override
    public void updateUserPassword(PasswordDto passwordDto) {
        User currentUser = SecurityUtil.getPrincipalFromSecurityContext();
        if (!passwordEncoder.matches(passwordDto.getCurrentPassword(), currentUser.getPassword())) {
            throw new InvalidPasswordException();
        }

        if (passwordEncoder.matches(passwordDto.getNewPassword(), currentUser.getPassword())) {
            throw new NewPasswordMatchesCurrentPasswordException();
        }

        if (!passwordDto.getNewPassword().equals(passwordDto.getConfirmNewPassword())) {
            throw new PasswordAndConfirmPasswordNotEqualException();
        }
        currentUser.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.save(currentUser);
    }

    @Override
    public void deleteUser() {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(InvalidCredentials.INVALID_EMAIL_OR_PASSWORD_MSG));

    }



}
