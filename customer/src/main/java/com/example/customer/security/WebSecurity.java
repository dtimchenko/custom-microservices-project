package com.example.customer.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    public static final String LOGIN_URL = "/api/v1/login";

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // make sure we use stateless session; session won't be used to
        // store user's state.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilter(authenticationFilter());
        // Add a filter to validate the tokens with every request
        http.addFilterBefore(authorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .antMatchers(LOGIN_URL).permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/customers").permitAll()
                .anyRequest().authenticated();
    }

    private AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManagerBean(), jwtUtils);
        authenticationFilter.setFilterProcessesUrl(LOGIN_URL);
        return authenticationFilter;
    }

    private AuthorizationFilter authorizationFilter() {
        return new AuthorizationFilter(jwtUtils);
    }
}
