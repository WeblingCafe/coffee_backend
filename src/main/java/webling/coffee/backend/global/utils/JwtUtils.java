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
import org.springframework.http.ResponseCookie;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Configuration
public class JwtUtils {

    public static final String ACCESS_AUTHORIZATION = "Access-" + AUTHORIZATION;
    public static final String REFRESH_AUTHORIZATION = "Refresh-" + AUTHORIZATION;
    public static final String SET_COOKIE = "Set-Cookie";
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

    public HttpHeaders getAuthHeaders(Long id, String email, String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ACCESS_AUTHORIZATION, generateAccessToken(id, email));

        ResponseCookie cookie = ResponseCookie.from(REFRESH_AUTHORIZATION, refreshToken)
                        .maxAge(7 * 24 * 60 * 60)
                        .path("/")
                        .secure(true)
                        .sameSite("None")
                        .httpOnly(true)
                        .build();

        headers.add(SET_COOKIE, cookie.toString());
        return headers;
    }

    public String generateAccessToken(Long id, String email) {
        return generateAccessToken(id,
                email,
                accessTokenTimeout);
    }

    public String generateRefreshToken(Long id, String email) {
        return generateRefreshToken(id,
                email,
                refreshTokenTimeout);
    }

    public static Long getMemberIdByAccessToken (String token) {
        return JWT.decode(getToken(token))
                .getClaim("id").asLong();
    }

    public static Long getMemberIdByRefreshToken (String token) {
        return JWT.decode(getToken(BEARER_TOKEN_PREFIX + token))
                .getClaim("id").asLong();
    }

    public static String getMemberEmailByRefreshToken(String token) {
        return JWT.decode(getToken(BEARER_TOKEN_PREFIX + token))
                .getAudience().get(0);
    }

    private String generateAccessToken(Long id, String email, long timeout) {
        return BEARER_TOKEN_PREFIX + generateJwtToken(id, email, timeout, ChronoUnit.SECONDS);
    }

    private String generateRefreshToken(Long id, String email, long timeout) {
        return generateJwtToken(id, email, timeout, ChronoUnit.MINUTES);
    }

    private String generateJwtToken(Long id, String email, long timeout, ChronoUnit chronoUnit) {
        final Instant now = Instant.now();

        return JWT.create()
                .withIssuer(issuer)
                .withAudience(email)
                .withClaim("id", id)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plus(timeout, chronoUnit)))
                .sign(getAlgorithm(secret));
    }

    private Algorithm getAlgorithm(String secret) {
        return Algorithm.HMAC512(secret);
    }

    public String getAccessToken(HttpServletRequest request) {
        return request.getHeader(ACCESS_AUTHORIZATION);
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
