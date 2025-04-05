package org.kylecodes.gm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kylecodes.gm.dtos.UserDto;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.repositories.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminService adminService = new AdminServiceImpl();

    private final Long VALID_USER_ID = 1L;
    private final String VALID_USER_EMAIL = "test@test.com";
    private final String VALID_USER_PASSWORD = "password";
    private final String VALID_USER_ROLE = "ROLE_USER";

    private final Long VALID_ADMIN_ID = 1L;
    private final String VALID_ADMIN_EMAIL = "admin@test.com";
    private final String VALID_ADMIN_PASSWORD = "password123";
    private final String VALID_ADMIN_ROLE = "ROLE_ADMIN";
    User user1;
    User user2;

    @BeforeEach
    public void init() {
        user1 = new User();
        user1.setId(VALID_USER_ID);
        user1.setEmail(VALID_USER_EMAIL);
        user1.setPassword(VALID_USER_PASSWORD);
        user1.setRole(VALID_USER_ROLE);
        user2 = new User();
        user2.setId(VALID_ADMIN_ID);
        user2.setEmail(VALID_ADMIN_EMAIL);
        user2.setPassword(VALID_ADMIN_PASSWORD);
        user2.setRole(VALID_ADMIN_ROLE);
    }
    @Test
    public void placeholder() {

    }

    @Test
    public void AdminService_GetAllUsers_ReturnUserList() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        final int EXPECTED_LIST_SIZE = 2;
        List<UserDto> users = adminService.getAllUsers();
        assertThat(user2.getRole()).isEqualTo(VALID_ADMIN_ROLE);
        assertThat(users.size()).isEqualTo(EXPECTED_LIST_SIZE);
    }
}
