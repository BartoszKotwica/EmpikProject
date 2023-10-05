package pl.coderslab.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.project.entity.GitHubUserData;
import pl.coderslab.project.entity.UserInfo;
import pl.coderslab.project.service.ApiCallService;
import pl.coderslab.project.service.GitHubApiService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final GitHubApiService gitHubApiService;
    private final ApiCallService apiCallService;
    private final ObjectMapper objectMapper;

    public UserController(GitHubApiService gitHubApiService, ApiCallService apiCallService, ObjectMapper objectMapper) {
        this.gitHubApiService = gitHubApiService;
        this.apiCallService = apiCallService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/{login}")
    public ResponseEntity<String> getUser(@PathVariable String login) {
        apiCallService.incrementRequestCount(login);

        GitHubUserData gitHubUserData = gitHubApiService.getGitHubUserData(login);

        if (gitHubUserData == null) {
            return ResponseEntity.internalServerError()
                    .body("Nie udało się pobrać danych użytkownika GitHub.");
        }

        double mian = gitHubUserData.getFollowers() * (2 + gitHubUserData.getPublicRepos());
        double calculations = 0;
        if (mian != 0) {
            calculations = 6.0 / mian;
        }

        // Tworzenie obiektu przechowującego odpowiednie dane
        UserInfo userInfo = new UserInfo(
                gitHubUserData.getId().toString(),
                gitHubUserData.getLogin(),
                gitHubUserData.getName(),
                gitHubUserData.getType(),
                gitHubUserData.getAvatarUrl(),
                gitHubUserData.getCreatedAt(),
                calculations
        )
        ;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        // Konwertujemy obiekt na JSON
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String jsonResponse = objectMapper.writeValueAsString(userInfo);
            return ResponseEntity.ok().headers(headers).body(jsonResponse);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Błąd podczas przetwarzania danych.");
        }
    }
}