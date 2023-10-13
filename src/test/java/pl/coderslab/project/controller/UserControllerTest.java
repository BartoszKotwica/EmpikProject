package pl.coderslab.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import pl.coderslab.project.dto.GitHubUserData;
import pl.coderslab.project.exceptions.CustomException;
import pl.coderslab.project.service.GitHubApiService;
import pl.coderslab.project.service.UserService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {
    @Test
    public void validGitHubUser() {
        // given
        GitHubApiService gitHubApiService = mock(GitHubApiService.class);
        UserService userService = mock(UserService.class);
        ObjectMapper objectMapper = new ObjectMapper();
        UserController userController = new UserController(gitHubApiService, userService, objectMapper);

        String login = "testUser";
        GitHubUserData gitHubUserData = new GitHubUserData();
        gitHubUserData.setId(1L);
        gitHubUserData.setLogin(login);
        gitHubUserData.setName("Test User");
        gitHubUserData.setType("User");
        gitHubUserData.setAvatarUrl("https://avatar.com/testUser");
        gitHubUserData.setCreatedAt(new Date());
        gitHubUserData.setFollowers(10L);
        gitHubUserData.setPublicRepos(5L);

        when(gitHubApiService.getGitHubUserData(login)).thenReturn(gitHubUserData);

        // when
        ResponseEntity<String> response = userController.getUser(login);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertNotNull(response.getBody());
    }

    @Test
    public void gitHubApiReturnsNull() {
        // given
        GitHubApiService gitHubApiService = mock(GitHubApiService.class);
        UserService userService = mock(UserService.class);
        ObjectMapper objectMapper = new ObjectMapper();
        UserController userController = new UserController(gitHubApiService, userService, objectMapper);

        String login = "testUser";

        when(gitHubApiService.getGitHubUserData(login)).thenReturn(null);

        // when
        ResponseEntity<String> response = userController.getUser(login);

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Nie udało się pobrać danych użytkownika GitHub.", response.getBody());
    }

    @Test
    public void gitHubApiServiceIsNull() {
        // given
        UserService userService = mock(UserService.class);
        ObjectMapper objectMapper = new ObjectMapper();
        UserController userController = new UserController(null, userService, objectMapper);

        String login = "testUser";

        // when and then
        assertThrows(CustomException.class, () -> {
            userController.getUser(login);
        });
    }

    @Test
    public void gitHubApiReturns404() {
        // given
        GitHubApiService gitHubApiService = mock(GitHubApiService.class);
        UserService userService = mock(UserService.class);
        ObjectMapper objectMapper = new ObjectMapper();
        UserController userController = new UserController(gitHubApiService, userService, objectMapper);

        String login = "testUser";

        when(gitHubApiService.getGitHubUserData(login)).thenReturn(null);

        // when
        ResponseEntity<String> response = userController.getUser(login);

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Nie udało się pobrać danych użytkownika GitHub.", response.getBody());
    }

    @Test
    public void incrementRequestCount() {
        // given
        GitHubApiService gitHubApiService = mock(GitHubApiService.class);
        UserService userService = mock(UserService.class);
        ObjectMapper objectMapper = new ObjectMapper();
        UserController userController = new UserController(gitHubApiService, userService, objectMapper);

        String login = "testUser";
        GitHubUserData gitHubUserData = new GitHubUserData();
        gitHubUserData.setId(1L);
        gitHubUserData.setLogin(login);
        gitHubUserData.setName("Test User");
        gitHubUserData.setType("User");
        gitHubUserData.setAvatarUrl("https://avatar.com/testUser");
        gitHubUserData.setCreatedAt(new Date());
        gitHubUserData.setFollowers(10L);
        gitHubUserData.setPublicRepos(5L);

        when(gitHubApiService.getGitHubUserData(login)).thenReturn(gitHubUserData);

        // when
        ResponseEntity<String> response = userController.getUser(login);

        // the
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertNotNull(response.getBody());

        verify(userService, times(1)).incrementRequestCount(login);
    }

    @Test
    public void jsonProcessingError() throws JsonProcessingException {
        // given
        GitHubApiService gitHubApiService = mock(GitHubApiService.class);
        UserService userService = mock(UserService.class);
        ObjectMapper objectMapper = mock(ObjectMapper.class);
        UserController userController = new UserController(gitHubApiService, userService, objectMapper);

        String login = "testUser";
        GitHubUserData gitHubUserData = new GitHubUserData();
        gitHubUserData.setId(1L);
        gitHubUserData.setLogin(login);

        when(gitHubApiService.getGitHubUserData(login)).thenReturn(gitHubUserData);
        when(objectMapper.writeValueAsString(any())).thenThrow(new JsonProcessingException("Error") {});

        // when
        ResponseEntity<String> response = userController.getUser(login);

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void correctHeaders() {
        // given
        GitHubApiService gitHubApiService = mock(GitHubApiService.class);
        UserService userService = mock(UserService.class);
        ObjectMapper objectMapper = new ObjectMapper();
        UserController userController = new UserController(gitHubApiService, userService, objectMapper);

        String login = "testUser";
        GitHubUserData gitHubUserData = new GitHubUserData();
        gitHubUserData.setId(1L);
        gitHubUserData.setLogin(login);
        gitHubUserData.setName("Test User");
        gitHubUserData.setType("User");
        gitHubUserData.setAvatarUrl("https://avatar.com/testUser");
        gitHubUserData.setCreatedAt(new Date());
        gitHubUserData.setFollowers(10L);
        gitHubUserData.setPublicRepos(5L);

        when(gitHubApiService.getGitHubUserData(login)).thenReturn(gitHubUserData);

        // when
        ResponseEntity<String> response = userController.getUser(login);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
    }
}