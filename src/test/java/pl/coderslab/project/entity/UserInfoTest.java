package pl.coderslab.project.entity;

import org.junit.jupiter.api.Test;
import pl.coderslab.project.dto.UserInfo;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserInfoTest {

    @Test
    public void createNewInstanceWithValidParameters() {
        String id = "123";
        String login = "john";
        String name = "John Doe";
        String type = "user";
        String avatarUrl = "https://example.com/avatar.jpg";
        Date createdAt = new Date();
        double calculations = 10.5;

        UserInfo userInfo = new UserInfo(id, login, name, type, avatarUrl, createdAt, calculations);

        assertEquals(id, userInfo.getId());
        assertEquals(login, userInfo.getLogin());
        assertEquals(name, userInfo.getName());
        assertEquals(type, userInfo.getType());
        assertEquals(avatarUrl, userInfo.getAvatarUrl());
        assertEquals(createdAt, userInfo.getCreatedAt());
        assertEquals(calculations, userInfo.getCalculations(), 0.001);
    }
}