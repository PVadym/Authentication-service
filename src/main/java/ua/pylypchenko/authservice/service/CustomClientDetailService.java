package ua.pylypchenko.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import ua.pylypchenko.authservice.repo.ClientRepo;

@Service
public class CustomClientDetailService implements ClientDetailsService {

    private  final ClientRepo clientRepo;

    @Autowired
    public CustomClientDetailService(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return clientRepo.findByClientId(clientId)
                .map( c -> {
                    BaseClientDetails clientDetails = new BaseClientDetails();
                    clientDetails.setAuthorizedGrantTypes(c.getAuthorizedGrantTypes());
                    clientDetails.setClientId(c.getClientId());
                    clientDetails.setClientSecret(c.getClientSecret());
                    clientDetails.setScope(c.getScopes());
                    return clientDetails;
                })
                .orElseThrow(() -> new ClientRegistrationException("client " + clientId+ "not found!"));
    }
}
