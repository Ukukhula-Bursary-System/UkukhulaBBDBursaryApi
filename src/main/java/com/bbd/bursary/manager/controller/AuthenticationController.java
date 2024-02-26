package com.bbd.bursary.manager.controller;

import com.bbd.bursary.manager.repository.InstituteInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final InstituteInfoRepository instituteInfoRepository;

    @Autowired
    public AuthenticationController(InstituteInfoRepository instituteInfoRepository) {
        this.instituteInfoRepository = instituteInfoRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Object authorities = securityContext.getAuthentication().getAuthorities();
        Object userDetails = securityContext.getAuthentication().getName();
        Optional<Long> institute = instituteInfoRepository.findIdByHeadOfDepartmentEmail(userDetails.toString());
        return new ResponseEntity<>(
                Map.of(
                        "authorities", authorities,
                        "user", userDetails,
                        "institute", institute.isPresent() ? institute.get() : -1
                ),
                HttpStatus.OK
        );
    }
}
