package com.hmaresc.TFG_Servidor.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request->
                        request.requestMatchers("/users/**")
                                .permitAll()
                                .requestMatchers("/recipe/**")
                                .permitAll()
                                .requestMatchers("/recipefood/**")
                                .permitAll()
                                .requestMatchers("/food/**")
                                .permitAll()
                                .requestMatchers("/usersrecipes/**")
                                .permitAll()
                                .requestMatchers("/userstats/**")
                                .permitAll()
                )
                .csrf(csrf -> csrf.disable());
        return  http.build();
    }
}
