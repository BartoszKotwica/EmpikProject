package pl.coderslab.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.project.entity.ApiCall;

import java.util.Optional;

public interface ApiCallRepository extends JpaRepository<ApiCall, Long> {
    Optional<ApiCall> findByLogin(String login);
}
