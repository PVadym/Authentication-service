package ua.pylypchenko.authservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Component;
import ua.pylypchenko.authservice.domain.Account;
import ua.pylypchenko.authservice.repo.AccountRepo;

import java.util.stream.Stream;

@EnableDiscoveryClient
@EnableResourceServer
@SpringBootApplication
public class AuthServiceApplication {



	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}

@Component
class AccountCLR implements CommandLineRunner{

	private PasswordEncoder passwordEncoder;

	private AccountRepo repo;

	@Autowired
	public AccountCLR(PasswordEncoder passwordEncoder, AccountRepo repo) {
		this.passwordEncoder = passwordEncoder;
		this.repo = repo;
	}

	@Override
	public void run(String... args) throws Exception {
		Stream.of(new Account("vadym", passwordEncoder.encode("pass")), new Account("anton", passwordEncoder.encode("pass2")))
				.forEach(a -> repo.save(a));
		repo.findAll().forEach(System.out::println);
	}
}
