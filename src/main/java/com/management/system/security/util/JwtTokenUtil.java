package com.management.system.security.util;

import com.management.system.security.vo.AppUserDetails;
import com.management.system.security.vo.LoginResponse;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Log4j2
@Component
public class JwtTokenUtil {

    private static String TOKEN_SECRET_KEY;
    private static Long ACCESS_TOKEN_VALIDITY;

    public JwtTokenUtil(@Value("${secret.key}") String secretKey,
                        @Value("${access.token.expiration}") Long accessValidity) {

        Assert.notNull(accessValidity, "Validity must not be null");
        Assert.hasText(secretKey, "Validity must not be null or empty");

        TOKEN_SECRET_KEY = secretKey;
        ACCESS_TOKEN_VALIDITY = accessValidity;
    }

    public static String generateToken(final String username, final String tokenId) {
        return Jwts.builder()
                .setId(tokenId)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setIssuer("skyros")
                .setExpiration(getTokenExpirationDate())
                .claim("created", Calendar.getInstance().getTime())
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET_KEY).compact();
    }

    public static LoginResponse prepareLoginResponse(AppUserDetails appUserDetails) {
        String accessTokenId = UUID.randomUUID().toString();
        String accessToken = generateToken(appUserDetails.getEmail(), accessTokenId);
        return LoginResponse.builder()
                .id(appUserDetails.getId())
                .email(appUserDetails.getEmail())
                .role(appUserDetails.getRole())
                .accessToken(accessToken)
                .build();
    }


    private static Date getTokenExpirationDate() {
        return new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY * 1000);
    }

    public String getUserNameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public String getTokenIdFromToken(String token) {
        return getClaims(token).getId();
    }

    public boolean isTokenValid(String token, AppUserDetails userDetails) {
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
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(TOKEN_SECRET_KEY).parseClaimsJws(token).getBody();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(TOKEN_SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.info("Invalid JWT Signature");
        } catch (MalformedJwtException ex) {
            log.info("Invalid JWT token");
            throw new SecurityException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.info("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.info("Unsupported JWT exception");
        } catch (IllegalArgumentException ex) {
            log.info("Jwt claims string is empty");
        }
        return false;
    }


}
