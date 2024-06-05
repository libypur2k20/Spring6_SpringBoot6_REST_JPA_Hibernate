package com.eazybytes.eazyschool.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // Permit All Requests inside the Web Application.

        // We need to disable CSRF for endpoints 'saveMsg' and 'h2-console'.
        http.csrf(csrf -> csrf
                        .ignoringRequestMatchers("/saveMsg")
                        .ignoringRequestMatchers("/public/**")
                        .ignoringRequestMatchers("/api/**")
                        .ignoringRequestMatchers("/data-api/**")
                        .ignoringRequestMatchers("/eazyschool/actuator/**")
                )
        .authorizeHttpRequests(requests ->
                        requests.requestMatchers("/","/home").permitAll()
                                .requestMatchers("/holidays/**").permitAll()
                                .requestMatchers("/contact").permitAll()
                                .requestMatchers("/about").permitAll()
                                .requestMatchers("/courses").permitAll()
                                .requestMatchers("/saveMsg").permitAll()
                                .requestMatchers("/assets/**").permitAll()
                                .requestMatchers("/dashboard").authenticated()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/logout").permitAll()
                                .requestMatchers("/displayMessages/**").hasRole("ADMIN")
                                .requestMatchers("/closeMsg/**").hasRole("ADMIN")
                                .requestMatchers("/public/**").permitAll()
                                .requestMatchers("/displayProfile").authenticated()
                                .requestMatchers("/updateProfile").authenticated()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/student/**").hasRole("STUDENT")
                                .requestMatchers("/api/**").authenticated()
                                .requestMatchers("/data-api/**").authenticated()
                                .requestMatchers("/eazyschool/actuator/**").hasRole("ADMIN")
                                //.requestMatchers("/profile/**").permitAll()  // For Spring Boot Data REST endpoint (if there is no base-path on application.properties file).
                                //.requestMatchers("/courseses/**").permitAll() //same
                                //.requestMatchers("/ccontacts/**").permitAll() //same
                                //.requestMatchers("/data-api/**").permitAll() // Allow public access to Spring Boot Data REST endpoints using the base-path.
        )
                .formLogin(loginConfigurer -> loginConfigurer.loginPage("/login")
                        .defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").permitAll())
                .logout(logoutConfigurer -> logoutConfigurer.logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll())
        .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
