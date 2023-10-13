package pl.coderslab.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.project.entity.User;
import pl.coderslab.project.repository.UserRepository;

import java.util.Optional;
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void incrementRequestCount(String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        User user = optionalUser.orElseGet(() -> {
            User newUser = new User();
            newUser.setLogin(login);
            newUser.setRequestCount(0L);
            return newUser;
        });

        user.setRequestCount(user.getRequestCount() + 1);
        userRepository.save(user);
    }

    public Long getRequestCount(String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        return optionalUser.map(User::getRequestCount).orElse(0L);
    }
}
