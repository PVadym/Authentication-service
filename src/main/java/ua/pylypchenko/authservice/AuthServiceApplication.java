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
import ua.pylypchenko.authservice.domain.Client;
import ua.pylypchenko.authservice.domain.Scope;
import ua.pylypchenko.authservice.repo.AccountRepo;
import ua.pylypchenko.authservice.repo.ClientRepo;

import java.util.Arrays;
import java.util.stream.Stream;

import static ua.pylypchenko.authservice.domain.GrantType.*;

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
	private ClientRepo clientRepo;
	private AccountRepo accountRepo;


	@Autowired
	public AccountCLR(PasswordEncoder passwordEncoder, ClientRepo clientRepo, AccountRepo accountRepo) {
		this.passwordEncoder = passwordEncoder;
		this.clientRepo = clientRepo;
		this.accountRepo = accountRepo;
	}


	@Override
	public void run(String... args) throws Exception {
		Stream.of(new Account("vadym", passwordEncoder.encode("pass")), new Account("anton", passwordEncoder.encode("pass2")))
				.forEach(a -> accountRepo.save(a));
		accountRepo.findAll().forEach(System.out::println);

		Stream.of(new Client("client", passwordEncoder.encode("secret"),
				Arrays.asList(refresh_token, password, client_credentials),
				Arrays.asList(Scope.webclient, Scope.mobileclient))).forEach(a -> clientRepo.save(a));

		clientRepo.findAll().forEach(System.out::println);

	}
}
