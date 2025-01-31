package com.example.Security.Salon.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthFilter jwtFilter;


    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)

       .authorizeHttpRequests(request ->
               request.requestMatchers("/api/v1/users/login", "/api/v1/users/signup", "/api/v1/Salon_Admin/signup","/api/v1/Salon_Admin/login", "/api/v1/General_Admin/signup","/api/v1/General_Admin/login", "/api-docs").permitAll()
                       .requestMatchers("/api/v1/salons/**").hasRole("SALON_ADMIN")
                       .requestMatchers("/api/v1/employees/**").hasRole("SALON_ADMIN")
                       .requestMatchers("/api/user/**").hasRole("USER")
               .anyRequest().authenticated())

       .formLogin(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

        .build();
     }
    @Bean
     public AuthenticationProvider authenticationProvider() {
         DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

         provider.setPasswordEncoder(new BCryptPasswordEncoder()
         );
         provider.setUserDetailsService(userDetailsService);
         return provider;
     }

     @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
     }

     @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();


     }
}

