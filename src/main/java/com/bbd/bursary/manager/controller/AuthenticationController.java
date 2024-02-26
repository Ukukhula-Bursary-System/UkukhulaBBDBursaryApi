package com.bbd.bursary.manager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Object authorities = securityContext.getAuthentication().getAuthorities();
        Object userDetails = securityContext.getAuthentication().getName();
        return new ResponseEntity<>(
                Map.of("authorities", authorities, "user", userDetails),
                HttpStatus.OK
        );
    }
}
