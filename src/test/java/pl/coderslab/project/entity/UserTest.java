package pl.coderslab.project.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void createUserWithValidLoginAndRequestCount() {
        //given
        String validLogin = "testUser";
        Long validRequestCount = 10L;

        // when
        User user = new User();
        user.setLogin(validLogin);
        user.setRequestCount(validRequestCount);

        // then
        assertEquals(validLogin, user.getLogin());
        assertEquals(validRequestCount, user.getRequestCount());
    }

}