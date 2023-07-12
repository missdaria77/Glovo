package com.example.glovo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(("/orders")).permitAll()
                                .anyRequest().authenticated())
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user =
                User.
                        withUsername("user1")
                        .password("{bcrypt}$2a$10$9Lf6jJ1MTp/PA8hEnjuF3.rpkhxIN95hCsFCibGcXiiAc36eAP1Iy")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);

    }
}
