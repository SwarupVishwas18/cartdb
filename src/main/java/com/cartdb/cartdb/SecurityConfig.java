package com.cartdb.cartdb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public UserDetailsService detailsService() {
        return new InMemoryUserDetailsManager(

                User.withUsername("admin").password("admin123").roles("ADMIN").build(),
                User.withUsername("user").password("user123").roles("USER").build(),
                User.withUsername("manager").password("manager123").roles("MANAGER").build());
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/h2-console").permitAll().anyRequest().authenticated())
                .headers(headers -> headers.frameOptions().disable())
                .httpBasic(Customizer.withDefaults());
        return http.build();


    }
    @Bean
    public static PasswordEncoder encoder() {
        return NoOpPasswordEncoder.getInstance();
    }


}
