package webling.coffee.backend.global.interceptors;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import webling.coffee.backend.domain.coupon.entity.Coupon;
import webling.coffee.backend.domain.coupon.service.core.CouponService;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.domain.user.service.core.UserService;
import webling.coffee.backend.global.annotation.AuthRequired;
import webling.coffee.backend.global.context.UserAuthentication;
import webling.coffee.backend.global.context.UserContext;
import webling.coffee.backend.global.enums.UserRole;
import webling.coffee.backend.global.redis.entity.RefreshToken;
import webling.coffee.backend.global.redis.service.RefreshTokenRedisService;
import webling.coffee.backend.global.responses.errors.codes.AuthenticationErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;
import webling.coffee.backend.global.utils.JwtUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    private final UserService userService;
    private final CouponService couponService;

    private final RefreshTokenRedisService refreshTokenRedisService;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {


        if (isAuthRequired(handler)) {
            AuthRequired authRequired = Objects.requireNonNull((HandlerMethod) handler).getMethodAnnotation(AuthRequired.class);

            String accessToken = jwtUtils.getAccessToken(request);
            String refreshToken = jwtUtils.getRefreshToken(request);

            if (isTokenEmpty(accessToken) || (isTokenEmpty(refreshToken))) {
                throw new RestBusinessException.Failure(AuthenticationErrorCode.INVALID_TOKEN);
            }

            try {
                jwtUtils.verifyToken(accessToken);

            } catch (TokenExpiredException e) {
                log.debug("Access Token is Expired");

                try {
                    RefreshToken refreshTokenFromRedis = refreshTokenRedisService.findByEmail(JwtUtils.getMemberEmailByToken(refreshToken));

                    if (!isRefreshTokenValid(refreshToken, refreshTokenFromRedis.getRefreshTokenValue())) {
                        throw new RestBusinessException.NotFound(AuthenticationErrorCode.REFRESH_TOKEN_NOT_FOUND);
                    }

                    accessToken = retrieveAccessToken(response, refreshTokenFromRedis.getRefreshTokenValue());

                } catch (RestBusinessException.NotFound e1) {
                    log.error("Refresh Token is Invalid");
                    throw new RestBusinessException.Failure(AuthenticationErrorCode.INVALID_TOKEN);

                }

            } catch (JWTVerificationException e) {
                log.error("Access Token is Invalid");
                throw new RestBusinessException.Failure(AuthenticationErrorCode.INVALID_TOKEN);
            }

            User user = userService.findByIdFetchCoupon(JwtUtils.getMemberIdByAccessToken(accessToken));

            if (isInvalidRole(authRequired, user.getUserRole())) {
                throw new RestBusinessException.Failure(AuthenticationErrorCode.ACCESS_DENIED);
            }

            UserContext.setAuthentication(UserAuthentication.from(user, user.getCoupons().size()));
        }
        return true;
    }

    private boolean isAuthRequired(final Object handler) {
        return handler instanceof HandlerMethod && ((HandlerMethod)handler).hasMethodAnnotation(AuthRequired.class);
    }

    private boolean isInvalidRole (final AuthRequired authRequired,
                                   final UserRole userRole) {
        return !userRole.contains(authRequired.roles());
    }

    private boolean isTokenEmpty(final String token) {
        return token == null || token.isBlank();
    }

    private String retrieveAccessToken (final @NotNull HttpServletResponse response,
                                        final @NotBlank String token) {

        String retrieveAccessToken = jwtUtils.generateAccessToken(
                JwtUtils.getMemberIdByRefreshToken(token),
                JwtUtils.getMemberEmailByToken(token));

        jwtUtils.setAuthorization (response, retrieveAccessToken);

        return retrieveAccessToken;
    }

    private boolean isRefreshTokenValid (String refreshToken, String refreshTokenFromRedis) {
        return refreshToken.equals(refreshTokenFromRedis);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        UserContext.clearAuthentication();
    }
}
