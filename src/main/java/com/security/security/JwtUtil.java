package com.security.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String username, String role) {
    	System.out.println("secret key is "+secret);
    	String token=Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    	System.out.println("token is "+token);
        return token;
    }

//    public Claims extractClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(secret.getBytes())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
    public Claims extractClaims(String token) {
        JwtParserBuilder parserBuilder = Jwts.parserBuilder();
        byte[] keyBytes = secret.getBytes();
        parserBuilder.setSigningKey(keyBytes);

        JwtParser parser = parserBuilder.build();

        Jws<Claims> parsedToken = parser.parseClaimsJws(token);

        Claims claims = parsedToken.getBody();

        return claims;
    }

}
