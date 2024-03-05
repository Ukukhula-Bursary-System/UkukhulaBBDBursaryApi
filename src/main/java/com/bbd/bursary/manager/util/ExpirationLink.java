package com.bbd.bursary.manager.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

public class ExpirationLink {

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 48;

    public static String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static String generateLink(String email) throws UnknownHostException {

        String baseUrl = "https://ukukhulabursaryfrontend.onrender.com";
        String token = generateToken(email);
        return baseUrl + "/upload-documents?token=" + token;
    }

    public static String getEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public static boolean isLinkValid(String token) {
        try {
            return !extractClaim(token, Claims::getExpiration).before(new Date());
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    private static <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static Key getSigningKey() {
        byte[] key = Decoders.BASE64URL.decode(System.getenv("TOKEN_SIGNING_KEY"));
        return Keys.hmacShaKeyFor(key);
    }
}
