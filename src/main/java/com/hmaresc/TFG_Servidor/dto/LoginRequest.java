package com.hmaresc.TFG_Servidor.dto;

public class LoginRequest {
    private String credentials;
    private String password;

    // Getters y setters

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
