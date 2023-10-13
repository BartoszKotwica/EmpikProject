package pl.coderslab.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.project.dto.GitHubUserData;
import pl.coderslab.project.exceptions.CustomException;


@Service
public class GitHubApiService {
    @Value("${github.api.url}")
    private String GITHUB_API_URL;
    private final RestTemplate restTemplate;

    @Autowired
    public GitHubApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GitHubUserData getGitHubUserData(String login) {
        if (restTemplate == null) {
            throw new CustomException("RestTemplate is null");
        }

        String apiUrl = GITHUB_API_URL + login;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

            HttpStatus statusCode = response.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                GitHubUserData gitHubUserData = new ObjectMapper().readValue(response.getBody(), GitHubUserData.class);
                return gitHubUserData;
            } else if (statusCode == HttpStatus.NOT_FOUND) {
                throw new CustomException("Użytkownik GitHub o podanym loginie nie został znaleziony.");
            } else if (statusCode == HttpStatus.UNAUTHORIZED) {
                throw new CustomException("Brak autoryzacji do pobrania danych użytkownika GitHub.");
            } else {
                throw new CustomException("Nie udało się pobrać danych użytkownika GitHub. Status HTTP: " + statusCode.value());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
