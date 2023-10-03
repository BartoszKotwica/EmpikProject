package pl.coderslab.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.project.entity.ApiCall;
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
        Optional<ApiCall> optionalApiCall = apiCallRepository.findByLogin(login);
        ApiCall apiCall = optionalApiCall.orElseGet(() -> {
            ApiCall newApiCall = new ApiCall();
            newApiCall.setLogin(login);
            newApiCall.setRequestCount(0L);
            return newApiCall;
        });

        apiCall.setRequestCount(apiCall.getRequestCount() + 1);
        apiCallRepository.save(apiCall);
    }

    public Long getRequestCount(String login) {
        Optional<ApiCall> optionalApiCall = apiCallRepository.findByLogin(login);
        return optionalApiCall.map(ApiCall::getRequestCount).orElse(0L);
    }
}
