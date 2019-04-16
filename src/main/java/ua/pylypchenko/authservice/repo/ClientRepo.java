package ua.pylypchenko.authservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pylypchenko.authservice.domain.Client;

import java.util.Optional;

public interface ClientRepo extends JpaRepository<Client, Long>{

    Optional<Client> findByClientId(String clientId);
}
