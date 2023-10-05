package pl.coderslab.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.project.entity.User;
import pl.coderslab.project.repository.ApiCallRepository;

import java.util.Optional;
@Service
public class ApiCallService {
    private final ApiCallRepository apiCallRepository;

    @Autowired
    public ApiCallService(ApiCallRepository apiCallRepository) {
        this.apiCallRepository = apiCallRepository;
    }

    public void incrementRequestCount(String login) {
        Optional<User> optionalApiCall = apiCallRepository.findByLogin(login);
        User apiCall = optionalApiCall.orElseGet(() -> {
            User newUser = new User();
            newUser.setLogin(login);
            newUser.setRequestCount(0L);
            return newUser;
        });

        apiCall.setRequestCount(apiCall.getRequestCount() + 1);
        apiCallRepository.save(apiCall);
    }

    public Long getRequestCount(String login) {
        Optional<User> optionalApiCall = apiCallRepository.findByLogin(login);
        return optionalApiCall.map(User::getRequestCount).orElse(0L);
    }
}
