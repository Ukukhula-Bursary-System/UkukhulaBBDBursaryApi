package com.bbd.bursary.manager.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoggedUser {
     public static boolean checkRole(List<String> roles) {
          AtomicBoolean sameRole = new AtomicBoolean(false);
          SecurityContextHolder.getContext().getAuthentication()
                  .getAuthorities()
                  .forEach(authority -> sameRole.set(
                          roles.contains(authority.getAuthority())
                  ));
          return sameRole.get();
     }

     public static ResponseEntity<?> unauthorizedResponse(String endpoint) {
          return new ResponseEntity<>(
                  unAuthorizeMessage(endpoint),
                  HttpStatus.UNAUTHORIZED
          );
     }

     private static Map<String, String> unAuthorizeMessage(String endpoint) {
          return Map.of(
                  "message",
                  String.format("login with correct credentials to access endpoint %s", endpoint)
          );
     }
}
