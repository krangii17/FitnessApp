package com.FitnessApp.controller.configuration;

import com.FitnessApp.model.Role;
import com.FitnessApp.model.State;
import com.FitnessApp.model.User;
import com.FitnessApp.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@Configuration
public class SpringSecurityWebAuxTestConfig {

    @Autowired
    BCryptPasswordEncoder encoder;

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {

        return new InMemoryUserDetailsManager(Arrays.asList(
                addUserDetails("valid@gmail.com", "123",
                        State.ACTIVE, Role.ROLE_USER),
                addUserDetails("disactive@gmail.com", "1234",
                        State.DISACTIVE, Role.ROLE_ADMIN),
                addUserDetails("guestRole@gmail.com", "1235",
                        State.ACTIVE, Role.ROLE_GUEST)));
    }

    private UserDetailsImpl addUserDetails(String email, String password, State state, Role role) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setState(state);
        user.setRole(role);
        return new UserDetailsImpl(user);
    }
}
