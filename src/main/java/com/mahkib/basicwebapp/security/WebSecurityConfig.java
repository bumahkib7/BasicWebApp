package com.mahkib.basicwebapp.security;

import com.mahkib.basicwebapp.models.User;
import com.mahkib.basicwebapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableAutoConfiguration
@AllArgsConstructor
@NoArgsConstructor
public class WebSecurityConfig {


    private UserService userService;


    @Bean
    public UserService userService() {
        return this.userService;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .antMatchers("/h2-console/**");
    }


    public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers("/h2-console/**").permitAll()
                        .antMatchers("/").permitAll()
                        .antMatchers("/login").permitAll()
                        .antMatchers("/register").permitAll()
                        .antMatchers("/static/**").permitAll()

                );
        http.httpBasic().and()
                .csrf().disable()
                .formLogin().disable()
                .logout().disable();

        return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService());
        return provider;
    }


    @Bean
    public ApplicationRunner runner() {
        return args -> userService().create(new User(null, "mahkib",
                passwordEncoder()
                        .encode("password123"),
                "ROLE_USER"));
    }

}
// end of class WebSecurityConfig