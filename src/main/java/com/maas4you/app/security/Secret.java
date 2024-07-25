package com.maas4you.app.security;

import lombok.Data;

@Data
public class Secret {

    String username;
    String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}