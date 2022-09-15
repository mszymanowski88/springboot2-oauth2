package com.example.springboot2oauth2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

@Configuration
public class SpringSecurityConfig {


    @Bean
    public PasswordEncoder getBcryptPasswordEncoder() {

        return new BCryptPasswordEncoder();

    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests((autz ->
                        autz
                                .antMatchers("/for-user").hasAnyRole("USER")
                                .antMatchers("/for-admin").hasAnyRole("ADMIN")
                                .antMatchers("/for-unknown").hasAnyRole("UNKNOWN")
                ))
                .formLogin((
                        httpSecurityFormLoginConfigurer ->
                                httpSecurityFormLoginConfigurer.permitAll())

                )
                .logout()
                .logoutSuccessUrl("/bye").permitAll();

        return httpSecurity.build();


    }

    @Bean
    public InMemoryUserDetailsManager getIneMemoryUserDetailManager() {
        UserDetails user = User.withUsername("jan")
                .password(getBcryptPasswordEncoder().encode("jan123"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(getBcryptPasswordEncoder().encode("admin123")).
                        roles("ADMIN")
                .build();


        UserDetails unknown = User.withUsername("unkown")
                .password(getBcryptPasswordEncoder().encode("uknown123")).
                        roles("UNKNOWN")
                .build();
        return new InMemoryUserDetailsManager(Arrays.asList(user, admin, unknown));

    }

}
