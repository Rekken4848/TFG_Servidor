package com.hmaresc.TFG_Servidor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hmaresc.TFG_Servidor.model.UserStats;

public class UserWithStatsDTO {
    private Long id;
    private String email;
    private String username;
    private String name;
    @JsonProperty("last_name")
    private String lastName;

    private UserStats userStats;

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserStats getUserStats() {
        return userStats;
    }

    public void setUserStats(UserStats userStats) {
        this.userStats = userStats;
    }
}
