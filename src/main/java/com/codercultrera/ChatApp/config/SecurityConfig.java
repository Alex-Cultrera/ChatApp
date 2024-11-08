package com.codercultrera.ChatApp.config;

import com.codercultrera.ChatApp.repository.UserRepository;
import com.codercultrera.ChatApp.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepo;
    private final MyUserDetailsService myUserDetailsService;

    public SecurityConfig (UserRepository userRepo, MyUserDetailsService myUserDetailsService) {
        this.userRepo = userRepo;
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeHttpRequests((request) -> {
                    request
                            .requestMatchers(HttpMethod.GET,"","/", "/login", "/register", "/users").permitAll()
                            .requestMatchers(HttpMethod.GET,"/**", "/login/**", "/register/**", "/users/**").permitAll()
                            .requestMatchers(HttpMethod.POST,"","/", "/login", "/register", "/users").permitAll()
                            .requestMatchers(HttpMethod.POST,"/**", "/login/**", "/register/**", "/users/**").permitAll()
                            .anyRequest().authenticated();
                })

                .authenticationProvider(authenticationProvider(myUserDetailsService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }


}
