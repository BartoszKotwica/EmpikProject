package pl.coderslab.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.project.entity.GitHubUserData;
import pl.coderslab.project.exceptions.CustomException;

import java.io.IOException;


@Service
public class GitHubApiService {
    private static final String GITHUB_API_URL = "https://api.github.com/users/";
    private final RestTemplate restTemplate;

    @Autowired
    public GitHubApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GitHubUserData getGitHubUserData(String login) {
        String apiUrl = GITHUB_API_URL + login;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                GitHubUserData gitHubUserData = new ObjectMapper().readValue(response.getBody(), GitHubUserData.class);
                return gitHubUserData;
            } else {
                // Obsługa odpowiedniego statusu HTTP, jeśli nie jest OK
                throw new CustomException("Nie udało się pobrać danych użytkownika GitHub. Status HTTP: " + response.getStatusCodeValue());
            }
        } catch (RestClientException | IOException e) {
            // Obsługa błędów związanych z RestTemplate
            throw new CustomException("Błąd podczas wywoływania API GitHub: " + e.getMessage());
        }
    }
}
