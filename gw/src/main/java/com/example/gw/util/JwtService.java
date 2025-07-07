package com.example.gw.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    public UUID extractUserId(String jwt) {
        Claims claims = extractAllClaims(jwt);
        return UUID.fromString(claims.getSubject());
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public List<String> extractRoles(String jwt) {
        Claims claims = extractAllClaims(jwt);
        Object rolesObject = claims.get("roles");
        List<String> roles = new ArrayList<>();
        if (rolesObject instanceof List<?>) {
            for (Object role : (List<?>) rolesObject) {
                if (role instanceof String) {
                    roles.add((String) role);
                }
            }
        }
        return roles;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }
}
