package com.example.authms.configuration;

import com.example.authms.api.model.AuthResponse;
import com.example.authms.api.model.Role;
import com.example.authms.api.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateAccessToken(MyUserPrincipal user) {
        return Jwts
                .builder()
                .setClaims(roles(user.getAuthorities()))
                .setSubject(user.getUserId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateAccessToken(User user) {
        return Jwts
                .builder()
                .setClaims(roles(user.getRoles()))
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(10).toInstant()))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMonths(1).toInstant()))
                .signWith(getSigningKey())
                .compact();
    }

    public AuthResponse generateToken(MyUserPrincipal user) {
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user.getUsername());

        return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    public AuthResponse generateToken(User user) {
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user.getEmail());

        return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
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

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Claims roles(Collection<GrantedAuthority> roles) {
        Map<String, Object> claims = new HashMap<>();
        Set<String> userRoles = new HashSet<>();
        for (GrantedAuthority role : roles) {
            userRoles.add("ROLE_" + role.getAuthority());
        }
        claims.put("roles", userRoles);
        return Jwts.claims(claims);
    }

    private Claims roles(List<Role> roles) {
        Map<String, Object> claims = new HashMap<>();
        Set<String> userRoles = new HashSet<>();
        for (Role role : roles) {
            userRoles.add("ROLE_" + role.getRoleName());
        }
        claims.put("roles", userRoles);
        return Jwts.claims(claims);
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }
}
