package com.FitnessApp.configs;

import com.FitnessApp.security.BasicAuthenticationEntryPointImpl;
import com.FitnessApp.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            "/",
            "/all/**",
            "/sign_up",
            "/getSecret",
            "/changePassword",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
    };

    final
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String REALM = "FITNESS_APPLICATION";
        http
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/login").hasAnyRole("ADMIN", "USER", "COACH")
                .antMatchers(HttpMethod.GET, "/muscles-groups").hasAnyRole("ADMIN", "USER", "COACH")
                .antMatchers(HttpMethod.POST, "/muscles-groups").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/muscles-groups").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/muscles-groups").hasRole("ADMIN")
                .antMatchers("user-programs/**").hasAnyRole("ADMIN", "USER", "COACH")
                .antMatchers("profiles/**").hasAnyRole("ADMIN", "USER", "COACH")
                .antMatchers("/admin/users**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/program-templates/**").hasAnyRole("ADMIN", "USER", "COACH")
                .antMatchers(HttpMethod.POST, "/program-templates/**").hasAnyRole("ADMIN", "COACH")
                .antMatchers(HttpMethod.PUT, "/program-templates/**").hasAnyRole("ADMIN", "COACH")
                .antMatchers(HttpMethod.DELETE, "/program-templates/**").hasAnyRole("ADMIN", "COACH")
                .antMatchers(HttpMethod.GET, "/exercises/**").hasAnyRole("ADMIN", "USER", "COACH")
                .antMatchers(HttpMethod.POST, "/exercise/**").hasAnyRole("ADMIN", "COACH")
                .antMatchers(HttpMethod.DELETE, "/exercise/**").hasAnyRole("ADMIN", "COACH")
                .anyRequest().authenticated()
                .and()
                .httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
                .and()
                .cors()
                .and()
                .csrf().disable();

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bcryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint getBasicAuthEntryPoint() {
        return new BasicAuthenticationEntryPointImpl();
    }
}
