package com.learn.hub.security.util;

import com.learn.hub.enums.UserRoleEnum;
import com.learn.hub.security.vo.AppUserDetails;
import com.learn.hub.security.vo.LoginResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Log4j2
@Component
public class JwtTokenUtil {

    @Value("${secret.key}")
    private String tokenSecretKey;
    @Value("${token.expiration.access}")
    private Long accessTokenExpiration;
    @Value("${token.expiration.refresh}")
    private Long refreshTokenExpiration;


    public String generateAccessToken(UserDetails userDetails) {
        return generateAccessToken(new HashMap<>(), userDetails, true);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateAccessToken(new HashMap<>(), userDetails, false);
    }

    public String generateAccessToken(Map<String, Object> claims, UserDetails userDetails, Boolean isAccessToken) {
        return buildToken(claims, userDetails, isAccessToken);
    }

    private String buildToken(Map<String, Object> claims, UserDetails userDetails, Boolean isAccessToken) {
        var authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(getTokenExpirationDate(isAccessToken))
                .setIssuer("tesla")
                .claim("authorities", authorities)
                .signWith(getSignInKey())
                .compact();
    }

    private Key getSignInKey() {
        byte[] decode = Base64.getDecoder().decode(tokenSecretKey);
        return Keys.hmacShaKeyFor(decode);
    }

    public LoginResponse prepareLoginResponse(AppUserDetails userDetails) {
        String accessToken = generateAccessToken(userDetails);
        GrantedAuthority grantedAuthority = userDetails.getAuthorities().stream().toList().get(0);
        return LoginResponse.builder()
                .id(userDetails.getId())
                .email(userDetails.getUsername())
                .role(UserRoleEnum.valueOf(grantedAuthority.getAuthority()))
                .accessToken(accessToken)
                .build();
    }


    private Date getTokenExpirationDate(Boolean isAccessToken) {
        long expiration = isAccessToken ? accessTokenExpiration * 1000 : refreshTokenExpiration * 1000;
        return new Date(System.currentTimeMillis() + expiration);
    }

    public String getUserNameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String getTokenIdFromToken(String token) {
        return extractClaim(token, Claims::getId);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        boolean tokenExpired = isTokenExpired(token);
        log.info("isTokenExpired : {}", tokenExpired);
        String username = getUserNameFromToken(token);
        log.info("username from token : {}", username);
        log.info("userDetails username : {}", userDetails.getUsername());
        Boolean isUserNameEqual = username.equalsIgnoreCase(userDetails.getUsername());
        boolean isTokenValid = isUserNameEqual && !tokenExpired;
        log.info("isTokenValid : {}", isTokenValid);
        return isTokenValid;
    }

    public boolean isTokenExpired(String token) {
        Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(tokenSecretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT Signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw new SecurityException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT exception");
        } catch (IllegalArgumentException ex) {
            log.error("Jwt claims string is empty");
        }
        return false;
    }


}
