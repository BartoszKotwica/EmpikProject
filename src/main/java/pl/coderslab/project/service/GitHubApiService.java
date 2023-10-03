package pl.coderslab.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.project.entity.GitHubUserData;
import pl.coderslab.project.exceptions.CustomException;


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
            ResponseEntity<GitHubUserData> response = restTemplate.getForEntity(apiUrl, GitHubUserData.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                // Obsługa odpowiedniego statusu HTTP, jeśli nie jest OK
                throw new CustomException("Nie udało się pobrać danych użytkownika GitHub. Status HTTP: " + response.getStatusCodeValue());
            }
        } catch (RestClientException e) {
            // Obsługa błędów związanych z RestTemplate
            throw new CustomException("Błąd podczas wywoływania API GitHub: " + e.getMessage());
        }
    }
}
