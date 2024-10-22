package com.mightyjava.test;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User regularUser = new User("tal", "password", Arrays.asList(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("PERM_READ_FOO")));

        User activeRegularUser = new User("tico", "password", Arrays.asList(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("PERM_READ_FOO")
        ));

        User adminUser = new User("teco", "password", Arrays.asList(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("PERM_READ_FOO"),
                new SimpleGrantedAuthority("PERM_WRITE_FOO"),
                new SimpleGrantedAuthority("PERM_MANAGE_FOO")
        ));

        return new InMemoryUserDetailsManager(Arrays.asList(
                regularUser, activeRegularUser, adminUser
        ));
    }
}
