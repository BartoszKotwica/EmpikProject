package pl.coderslab.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import pl.coderslab.project.entity.GitHubUserData;
import pl.coderslab.project.entity.UserInfo;
import pl.coderslab.project.exceptions.CustomException;
import pl.coderslab.project.service.ApiCallService;
import pl.coderslab.project.service.GitHubApiService;

import java.util.Date;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

//    @Test
//    public void test_response_entity_with_user_info() {
//        // Arrange
//        String login = "testLogin";
//        GitHubUserData gitHubUserData = new GitHubUserData();
//        gitHubUserData.setId(1L);
//        gitHubUserData.setLogin(login);
//        gitHubUserData.setName("Test User");
//        gitHubUserData.setType("User");
//        gitHubUserData.setAvatarUrl("https://example.com/avatar");
//        gitHubUserData.setCreatedAt(new Date());
//        gitHubUserData.setFollowers(10L);
//        gitHubUserData.setPublicRepos(5L);
//
//        ApiCallService apiCallService = mock(ApiCallService.class);
//        GitHubApiService gitHubApiService = mock(GitHubApiService.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        UserController userController = new UserController(gitHubApiService, apiCallService, objectMapper);
//
//        HttpHeaders expectedHeaders = new HttpHeaders();
//        expectedHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//        UserInfo expectedUserInfo = new UserInfo(
//                gitHubUserData.getId().toString(),
//                gitHubUserData.getLogin(),
//                gitHubUserData.getName(),
//                gitHubUserData.getType(),
//                gitHubUserData.getAvatarUrl(),
//                gitHubUserData.getCreatedAt(),
//                6.0 / (gitHubUserData.getFollowers() * (2 + gitHubUserData.getPublicRepos()))
//        );
//
//        String expectedJsonResponse;
//        try {
//            expectedJsonResponse = objectMapper.writeValueAsString(expectedUserInfo);
//        } catch (JsonProcessingException e) {
//            fail("Failed to convert UserInfo to JSON");
//            return;
//        }
//
//        ResponseEntity<String> expectedResponseEntity = ResponseEntity.ok().headers(expectedHeaders).body(expectedJsonResponse);
//
//        when(gitHubApiService.getGitHubUserData(login)).thenReturn(gitHubUserData);
//
//        // Act
//        userController.getUser(login);
//
//        // Assert
//        verify(apiCallService, times(1)).incrementRequestCount(login);
//    }

    @Test
    public void test_calculations_field() {
        // Arrange
        String login = "testLogin";
        GitHubUserData gitHubUserData = new GitHubUserData();
        gitHubUserData.setId(1L);
        gitHubUserData.setLogin(login);
        gitHubUserData.setName("Test User");
        gitHubUserData.setType("User");
        gitHubUserData.setAvatarUrl("https://example.com/avatar");
        gitHubUserData.setCreatedAt(new Date());
        gitHubUserData.setFollowers(10L);
        gitHubUserData.setPublicRepos(5L);

        ApiCallService apiCallService = mock(ApiCallService.class);
        GitHubApiService gitHubApiService = mock(GitHubApiService.class);
        ObjectMapper objectMapper = new ObjectMapper();

        UserController userController = new UserController(gitHubApiService, apiCallService, objectMapper);

        HttpHeaders expectedHeaders = new HttpHeaders();
        expectedHeaders.setContentType(MediaType.APPLICATION_JSON);

        UserInfo expectedUserInfo = new UserInfo(
                gitHubUserData.getId().toString(),
                gitHubUserData.getLogin(),
                gitHubUserData.getName(),
                gitHubUserData.getType(),
                gitHubUserData.getAvatarUrl(),
                gitHubUserData.getCreatedAt(),
                6.0 / (gitHubUserData.getFollowers() * (2 + gitHubUserData.getPublicRepos()))
        );

        String expectedJsonResponse;
        try {
            expectedJsonResponse = objectMapper.writeValueAsString(expectedUserInfo);
        } catch (JsonProcessingException e) {
            fail("Failed to convert UserInfo to JSON");
            return;
        }

        ResponseEntity<String> expectedResponseEntity = ResponseEntity.ok().headers(expectedHeaders).body(expectedJsonResponse);

        when(gitHubApiService.getGitHubUserData(login)).thenReturn(gitHubUserData);

        // Act
        ResponseEntity<String> actualResponseEntity = userController.getUser(login);

        // Assert
        assertEquals(expectedUserInfo.getCalculations(), actualResponseEntity.getBody().getClass());
    }

    @Test
    public void test_github_api_call_failure() {
        // Arrange
        String login = "testLogin";

        ApiCallService apiCallService = mock(ApiCallService.class);
        GitHubApiService gitHubApiService = mock(GitHubApiService.class);
        ObjectMapper objectMapper = new ObjectMapper();

        UserController userController = new UserController(gitHubApiService, apiCallService, objectMapper);

        when(gitHubApiService.getGitHubUserData(login)).thenThrow(new CustomException("Failed to get GitHub user data"));

        // Act
        ResponseEntity<String> actualResponseEntity = userController.getUser(login);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponseEntity.getStatusCode());
        assertEquals("Failed to get GitHub user data", actualResponseEntity.getBody());
    }

    @Test
    public void test_json_processing_failure() throws JsonProcessingException {
        // Arrange
        String login = "testLogin";
        GitHubUserData gitHubUserData = new GitHubUserData();
        gitHubUserData.setId(1L);
        gitHubUserData.setLogin(login);
        gitHubUserData.setName("Test User");
        gitHubUserData.setType("User");
        gitHubUserData.setAvatarUrl("https://example.com/avatar");
        gitHubUserData.setCreatedAt(new Date());
        gitHubUserData.setFollowers(10L);
        gitHubUserData.setPublicRepos(5L);

        ApiCallService apiCallService = mock(ApiCallService.class);
        GitHubApiService gitHubApiService = mock(GitHubApiService.class);
        ObjectMapper objectMapper = mock(ObjectMapper.class);

        UserController userController = new UserController(gitHubApiService, apiCallService, objectMapper);

        when(gitHubApiService.getGitHubUserData(login)).thenReturn(gitHubUserData);
        when(objectMapper.writeValueAsString(any(UserInfo.class))).thenThrow(new JsonProcessingException("Failed to process JSON") {});

        // Act
        ResponseEntity<String> actualResponseEntity = userController.getUser(login);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponseEntity.getStatusCode());
        assertEquals("Failed to process JSON", actualResponseEntity.getBody());
    }
}