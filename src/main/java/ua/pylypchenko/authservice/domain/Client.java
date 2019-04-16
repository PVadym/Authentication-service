package ua.pylypchenko.authservice.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Client {

    @Id
    @GeneratedValue
    private Long Id;

    private String clientId;

    @Column(length = 70)
    private String clientSecret;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @Enumerated(EnumType.STRING)
    @JoinTable(name = "grant_types", joinColumns = @JoinColumn(name = "client_id"))
    private List<GrantType> authorizedGrantTypes;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @Enumerated(EnumType.STRING)
    @JoinTable(name = "scopes", joinColumns = @JoinColumn(name = "client_id"))
    private List<Scope> scopes;

    public Client(String clientId, String clientSecret, List<GrantType> authorizedGrantTypes, List<Scope> scopes) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authorizedGrantTypes = authorizedGrantTypes;
        this.scopes = scopes;
    }

    public Client() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public List<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes.stream()
                .map(GrantType::name)
                .collect(Collectors.toList());
    }

    public void setAuthorizedGrantTypes(List<GrantType> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public List<String> getScopes() {
        return scopes.stream()
                .map(Scope::name)
                .collect(Collectors.toList());
    }

    public void setScopes(List<Scope> scopes) {
        this.scopes = scopes;
    }

    @Override
    public String toString() {
        return "Client{" +
                "Id=" + Id +
                ", clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", authorizedGrantTypes=" + authorizedGrantTypes +
                ", scopes=" + scopes +
                '}';
    }
}
