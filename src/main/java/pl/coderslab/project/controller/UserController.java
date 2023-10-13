package pl.coderslab.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.project.dto.GitHubUserData;
import pl.coderslab.project.dto.UserInfo;
import pl.coderslab.project.exceptions.CustomException;
import pl.coderslab.project.service.GitHubApiService;
import pl.coderslab.project.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final GitHubApiService gitHubApiService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserController(GitHubApiService gitHubApiService, UserService userService, ObjectMapper objectMapper) {
        this.gitHubApiService = gitHubApiService;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/{login}")
    public ResponseEntity<String> getUser(@PathVariable String login) {
        if (gitHubApiService == null) {
            throw new CustomException("GitHubApiService is null");
        }


        userService.incrementRequestCount(login);

        GitHubUserData gitHubUserData = retryGetGitHubUserData(login);

        if (gitHubUserData == null) {
            return ResponseEntity.internalServerError()
                    .body("Nie udało się pobrać danych użytkownika GitHub.");
        }

        double mianownik = gitHubUserData.getFollowers() * (2 + gitHubUserData.getPublicRepos());
        double calculations = 0;
        if (mianownik != 0) {
            calculations = 6.0 / mianownik;
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
        );


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

    private GitHubUserData retryGetGitHubUserData(String login) {
        int maxRetries = 5;
        int retries = 0;
        while (retries < maxRetries) {
            try {
                return gitHubApiService.getGitHubUserData(login);
            } catch (CustomException e) {
                // GitHubApiService zgłosił wyjątek, spróbuj ponownie za jakiś czas
                retries++;
                try {
                    // Poczekaj 2 sekundy przed ponowną próbą
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                }
            }
        }
        return null; // Próby ponowienia wyczerpały się
    }
}