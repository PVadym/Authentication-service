package ua.pylypchenko.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.pylypchenko.authservice.repo.AccountRepo;

@Service
public class AccountUserDetailService implements UserDetailsService {

    private AccountRepo repo;

    @Autowired
    public AccountUserDetailService(AccountRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        return repo.findByName(s)
                .map(account -> User.builder()
                        .username(account.getName())
                        .password(account.getPassword())
                        .accountExpired(!account.isActive())
                        .accountLocked(!account.isActive())
                        .disabled(!account.isActive())
                        .credentialsExpired(!account.isActive())
                        .authorities(AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER"))
                        .build()

                )
                .orElseThrow(() -> new UsernameNotFoundException("user not exists" + s));
    }
}
