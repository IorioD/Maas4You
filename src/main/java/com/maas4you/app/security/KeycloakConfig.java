package com.maas4you.app.security;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
    
    @Bean
    KeycloakSpringBootConfigResolver configResolver(){
        return new KeycloakSpringBootConfigResolver();
    }

}
