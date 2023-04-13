package webling.coffee.backend.global.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import webling.coffee.backend.global.annotation.AuthRequired;
import webling.coffee.backend.global.errors.codes.AuthenticationErrorCode;
import webling.coffee.backend.global.errors.exceptions.RestBusinessException;
import webling.coffee.backend.global.utils.JwtUtils;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {

        if (isAuthRequired(handler)) {
            AuthRequired authRequired = ((HandlerMethod) handler).getMethodAnnotation(AuthRequired.class);

            String accessToken = jwtUtils.getAccessToken(request);
            String refreshToken = jwtUtils.getRefreshToken(request);

            if (isTokenEmpty(accessToken) || (isTokenEmpty(refreshToken))) {
                throw new RestBusinessException(AuthenticationErrorCode.INVALID_TOKEN);
            }



        }


        return false;
    }

    private boolean isAuthRequired(final Object handler) {
        return handler instanceof HandlerMethod && ((HandlerMethod)handler).hasMethodAnnotation(AuthRequired.class);
    }

    private boolean isTokenEmpty(final String token) {
        return token == null || token.isBlank();
    }


}
