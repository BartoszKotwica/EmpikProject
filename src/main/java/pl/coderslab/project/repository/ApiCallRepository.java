package pl.coderslab.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.project.entity.User;

import java.util.Optional;

public interface ApiCallRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
