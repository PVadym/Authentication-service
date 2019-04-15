package ua.pylypchenko.authservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Component;
import ua.pylypchenko.authservice.domain.Account;
import ua.pylypchenko.authservice.repo.AccountRepo;

import javax.annotation.Resource;
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

//	private PasswordEncoder passwordEncoder;

	private AccountRepo repo;

	@Autowired
	public AccountCLR(AccountRepo repo) {
		this.repo = repo;
	}

	@Override
	public void run(String... args) throws Exception {
		Stream.of(new Account("vadym", ("{noop}pass")), new Account("anton", ("{noop}pass2")))
				.forEach(a -> repo.save(a));
		repo.findAll().forEach(System.out::println);
	}
}
