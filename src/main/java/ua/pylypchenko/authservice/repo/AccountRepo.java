package ua.pylypchenko.authservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ua.pylypchenko.authservice.domain.Account;

import java.util.Optional;

@RepositoryRestResource
public interface AccountRepo extends JpaRepository<Account, Long> {

    Optional<Account> findByName(String name);
}
