package webling.coffee.backend.global.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Configuration
public class JwtUtils {

    public static final String ACCESS_AUTHORIZATION = "Access-" + AUTHORIZATION;
    public static final String REFRESH_AUTHORIZATION = "Refresh-" + AUTHORIZATION;
    public static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private final String issuer;
    private final String secret;
    private final long refreshTokenTimeout;
    private final long accessTokenTimeout;

    public JwtUtils(@Value("${jwt.issuer}") String issuer,
                    @Value("${jwt.access.timeout-of-minutes}") long accessTokenTimeout,
                    @Value("${jwt.refresh.timeout-of-minutes}") long refreshTokenTimeout,
                    @Value("${jwt.secret}") String secret) {
        this.issuer = issuer;
        this.secret = secret;
        this.refreshTokenTimeout = refreshTokenTimeout;
        this.accessTokenTimeout = accessTokenTimeout;
    }

    public HttpHeaders getAuthHeaders(Long id, String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ACCESS_AUTHORIZATION, generateAccessToken(id, email));
        headers.add(REFRESH_AUTHORIZATION, generateRefreshToken(id, email));
        return headers;
    }

    public void setAuthorization(HttpServletResponse response, String retrieveAccessToken) {
        response.setHeader(ACCESS_AUTHORIZATION, retrieveAccessToken);
    }

    public String generateAccessToken(Long id, String email) {
        return generateToken(id,
                email,
                accessTokenTimeout);
    }

    public String generateRefreshToken(Long id, String email) {
        return generateToken(id,
                email,
                refreshTokenTimeout);
    }

    public static Long getMemberIdByToken (String token) {
        return JWT.decode(getToken(token))
                .getClaim("id").asLong();
    }

    public static String getMemberEmailByToken (String token) {
        return JWT.decode(getToken(token))
                .getAudience().get(0);
    }

    private String generateToken(Long id, String email, long timeout) {
        final Instant now = Instant.now();

        return BEARER_TOKEN_PREFIX + JWT.create()
                .withIssuer(issuer)
                .withAudience(email)
                .withClaim("id", id)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plus(timeout, ChronoUnit.MINUTES)))
                .sign(getAlgorithm(secret));
    }

    private Algorithm getAlgorithm(String secret) {
        return Algorithm.HMAC512(secret);
    }

    public String getAccessToken(HttpServletRequest request) {
        return request.getHeader(ACCESS_AUTHORIZATION);
    }

    public String getRefreshToken(HttpServletRequest request) {
        return request.getHeader(REFRESH_AUTHORIZATION);
    }

    public void verifyToken (String token) {
        getRequireToken().verify(getToken(token));
    }

    private JWTVerifier getRequireToken () {
        return JWT.require(getAlgorithm(secret))
                .withIssuer(issuer)
                .build();
    }

    private static String getToken(String token) {
        return token.substring(BEARER_TOKEN_PREFIX.length());
    }


}
