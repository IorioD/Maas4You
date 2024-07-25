package com.maas4you.app;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;

import com.maas4you.app.security.Secret;

@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource datasource() throws URISyntaxException {

        VaultTemplate vaultTemplate = new VaultTemplate( VaultEndpoint.from(new URI("http://vault:8200")),
        new TokenAuthentication(""));// <- Insert your Vault token here to connect to the endpoint
        Secret secret = new Secret();
        VaultResponseSupport<Secret> response = vaultTemplate.read("secret/myapp", Secret.class);
        
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://db:3306/ssd_app")
                .username(response.getData().getUsername())
                .password(response.getData().getPassword())
                .build();
    }
}