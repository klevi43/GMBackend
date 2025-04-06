package org.kylecodes.gm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kylecodes.gm.contexts.SecurityContextForTests;
import org.kylecodes.gm.dtos.AuthUserDto;
import org.kylecodes.gm.dtos.RegisterDto;
import org.kylecodes.gm.dtos.UserDto;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.exceptions.AlreadyLoggedInException;
import org.kylecodes.gm.repositories.UserRepository;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    Authentication authentication;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    private RegisterDto registerDto;
    private AuthUserDto authUserDto;
    private User user;
    private final Long VALID_USER_ID = 1L;
    private final String VALID_USER_EMAIL = "test@test.com";
    private final String VALID_USER_NEW_EMAIL = "newTest@test.com";
    private final String VALID_USER_PASSWORD = "password";
    private final String VALID_USER_NEW_PASSWORD = "newPassword";
    private final String VALID_USER_ROLE = "ROLE_USER";

    @BeforeEach
    public void init() {
        user = new User();
        user.setId(VALID_USER_ID);
        user.setEmail(VALID_USER_EMAIL);
        user.setPassword(VALID_USER_PASSWORD);
        user.setRole(VALID_USER_ROLE);




        registerDto = new RegisterDto();


        registerDto.setEmail(VALID_USER_EMAIL);
        registerDto.setPassword(VALID_USER_PASSWORD);
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());

    }
    @Test
    public void UserService_RegisterNewUser_ReturnUserDto() {

        when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);

        UserDto savedUser = userService.registerNewUser(registerDto);

        assertThat(savedUser.getEmail()).isEqualTo(registerDto.getEmail());
        assertThat(savedUser.getRole()).isEqualTo(user.getRole());
    }

    @Test
    public void UserService_RegisterNewUser_ThrowIsAlreadyLoggedInException() {
        SecurityContextForTests context = new SecurityContextForTests();
        context.setSecurityContextAsIfUserIsAlreadyAuthenticated();
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        assertThrows(AlreadyLoggedInException.class, () -> userService.registerNewUser(registerDto));


    }

    @Test
    public void UserService_UpdateUserEmailAndPassword_ReturnUpdatedUserDto() {
        SecurityContextForTests context = new SecurityContextForTests();
        context.createSecurityContextToReturnAuthenticatedUser(user);
        when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);
        authUserDto = new AuthUserDto();
        authUserDto.setEmail(VALID_USER_NEW_EMAIL);
        authUserDto.setPassword(VALID_USER_NEW_PASSWORD);

        UserDto updatedUser = userService.updateUserInfo(authUserDto);

        assertThat(updatedUser.getEmail()).isEqualTo(authUserDto.getEmail());

    }

    @Test
    public void UserService_UpdateUserEmail_ReturnUpdatedUserDto() {
        SecurityContextForTests context = new SecurityContextForTests();
        context.createSecurityContextToReturnAuthenticatedUser(user);
        when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);
        authUserDto = new AuthUserDto();
        authUserDto.setEmail(VALID_USER_NEW_EMAIL);


        UserDto updatedUser = userService.updateUserInfo(authUserDto);

        assertThat(updatedUser.getEmail()).isEqualTo(authUserDto.getEmail());

    }

    @Test
    public void UserService_DeleteUser_ReturnNothing() {
        SecurityContextForTests context = new SecurityContextForTests();
        context.createSecurityContextToReturnAuthenticatedUser(user);
        
        assertAll(() -> userService.deleteUser());

        assertThat(userRepository.existsById(user.getId())).isFalse();
    }


}
