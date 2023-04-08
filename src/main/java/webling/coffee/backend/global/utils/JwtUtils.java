package webling.coffee.backend.global.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Configuration
public class JwtUtils {

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

}
