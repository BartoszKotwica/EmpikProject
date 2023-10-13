package pl.coderslab.project.entity;

import org.junit.jupiter.api.Test;
import pl.coderslab.project.dto.GitHubUserData;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GitHubUserDataTest {

    @Test
    public void allFieldsCorrectlySet() {
        GitHubUserData userData = new GitHubUserData();
        userData.setId(123L);
        userData.setLogin("testUser");
        userData.setName("Test User");
        userData.setType("user");
        userData.setAvatarUrl("https://example.com/avatar.png");
        userData.setCreatedAt(new Date());
        userData.setFollowers(10L);
        userData.setPublicRepos(5L);

        assertEquals(123L, userData.getId());
        assertEquals("testUser", userData.getLogin());
        assertEquals("Test User", userData.getName());
        assertEquals("user", userData.getType());
        assertEquals("https://example.com/avatar.png", userData.getAvatarUrl());
        assertNotNull(userData.getCreatedAt());
        assertEquals(10L, userData.getFollowers());
        assertEquals(5L, userData.getPublicRepos());
    }

}