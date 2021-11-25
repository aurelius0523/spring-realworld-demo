package com.aurelius.springrealworld.security;


import com.aurelius.springrealworld.repository.entities.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenUtil {
    private final String jwtSecret = "zdtlD3JK56m6wTTgsNFhqzjqP";
    private final Date ONE_WEEK_EXPIRATION_FROM_NOW = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);

    /**
     * Creates an accessToken that follows JsonWebToken(JWT) spec. You can read more at
     * <a href="https://jwt.io/">jwt.io</a>
     *
     * @param user userEntity from database
     * @return JWT token
     */
    public String generateAccessToken(UserEntity user) {
        String jwtIssuer = "example.io";
        return Jwts.builder()
                .setSubject(String.format("%s", user.getUsername()))
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(ONE_WEEK_EXPIRATION_FROM_NOW)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Retrieve username from <i>sub</i> claims in JWT token. <i>sub</i>
     * is a reserved claim in JWT. Feel free to use any other claims by
     * invoking {@link JwtBuilder#setClaims(Claims)} in {@link #generateAccessToken(UserEntity)}.
     * <p/>
     * Be aware that {@link JwtBuilder#setClaims(Map)} will override default claims so subclassing
     * {@link Claims} or {@link DefaultClaims} will be a better choice
     *
     * @param token JWT token
     * @return username
     */
    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * Get expiration date from JWT token
     *
     * @param token JWT token
     * @return expiration date
     */
    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    /**
     * Validate JWT Token token is considered valid if it passes signing and
     * has not expired
     *
     * @param token JWT token
     * @return boolean indicating if token is valid
     */
    public boolean isJwtTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);

            return new Date().before(getExpirationDate(token));
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }

}
