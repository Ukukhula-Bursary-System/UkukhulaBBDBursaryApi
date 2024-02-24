package com.bbd.bursary.manager.security;

import com.bbd.bursary.manager.model.UserDetails;
import com.bbd.bursary.manager.repository.UserDetailsRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private JwtDecoder jwtDecoder;
    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public JWTAuthenticationFilter(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || authHeader.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtDecoder = JwtDecoders.fromIssuerLocation("https://accounts.google.com");

        String token = authHeader.substring(7);
        Jwt decodedToken = jwtDecoder.decode(token);
        String email = decodedToken.getClaim("email").toString();

        if (!email.isEmpty()) {
            UserDetails user = userDetailsRepository.findByEmail(email)
                    .orElseThrow(() -> new AuthorizationServiceException("Email " + email + " not registered!"));
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

            JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(
                    decodedToken, user.getAuthorities(), user.getEmail()
            );
            securityContext.setAuthentication(jwtAuthenticationToken);
            SecurityContextHolder.setContext(securityContext);
        }
        filterChain.doFilter(request, response);
    }
}
