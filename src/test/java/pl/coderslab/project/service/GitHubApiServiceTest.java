package pl.coderslab.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.project.dto.GitHubUserData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GitHubApiServiceTest {

    @Test
    void getGitHubUserData() throws JsonProcessingException {
        //given
        String login = "validLogin";
        GitHubUserData expectedUserData = new GitHubUserData();
        expectedUserData.setLogin(login);
        // Mock the restTemplate
        RestTemplate restTemplateMock = Mockito.mock(RestTemplate.class);
        ResponseEntity<String> responseMock = new ResponseEntity<>(new ObjectMapper().writeValueAsString(expectedUserData), HttpStatus.OK);
        Mockito.when(restTemplateMock.getForEntity(Mockito.anyString(), Mockito.eq(String.class))).thenReturn(responseMock);
        GitHubApiService gitHubApiService = new GitHubApiService(restTemplateMock);

        // when
        GitHubUserData actualUserData = gitHubApiService.getGitHubUserData(login);

        // then
        assertNotNull(actualUserData);
        assertEquals(expectedUserData.getLogin(), actualUserData.getLogin());
    }
}