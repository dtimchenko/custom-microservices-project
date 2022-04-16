package com.example.customer.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";

    private final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals(WebSecurity.LOGIN_URL)){
            filterChain.doFilter(request, response);
        } else {

            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if(Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith(BEARER)){

                try {
                    String token = authorizationHeader.substring(BEARER.length());
                    UsernamePasswordAuthenticationToken authenticationToken = jwtUtils.parseToken(token);

                    // After setting the Authentication in the context, we specify
                    // that the current user is authenticated. So it passes the
                    // Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    filterChain.doFilter(request, response);
                }catch (Exception ex){
                    log.error("Error logging in: {}", ex.getMessage());
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    new ObjectMapper().writeValue(response.getOutputStream(), ex.getMessage());
                }

            }else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
