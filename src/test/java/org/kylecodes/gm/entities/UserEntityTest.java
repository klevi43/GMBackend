package org.kylecodes.gm.entities;

import org.junit.jupiter.api.Test;
import org.kylecodes.gm.constants.Roles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserEntityTest {
    private final long VALID_ID = 1L;
    private final String VALID_EMAIL = "user@email.com";
    private final String VALID_PASSWORD = "password";
    private final String VALID_ROLE = Roles.USER;

    private final long INVALID_ID = 1L;
    private final String INVALID_EMAIL = "user";
    private final String INVALID_PASSWORD = "pa";
    private final String INVALID_ROLE = null;
    User user;
    @Test
    public void User_ConstructorTest_ReturnInstantiatedUser() {
        user = new User(VALID_ID, VALID_EMAIL, VALID_PASSWORD, VALID_ROLE, null);

        assertThat(user).isNotNull();
    }


}
