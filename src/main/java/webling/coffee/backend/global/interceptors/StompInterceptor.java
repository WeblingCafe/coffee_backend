package webling.coffee.backend.global.interceptors;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import webling.coffee.backend.global.redis.entity.RefreshToken;
import webling.coffee.backend.global.redis.service.RefreshTokenRedisService;
import webling.coffee.backend.global.responses.errors.codes.AuthenticationErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;
import webling.coffee.backend.global.utils.JwtUtils;

import static webling.coffee.backend.global.utils.JwtUtils.ACCESS_AUTHORIZATION;
import static webling.coffee.backend.global.utils.JwtUtils.REFRESH_AUTHORIZATION;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompInterceptor implements ChannelInterceptor {

    private final JwtUtils jwtUtils;

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (accessor.getCommand() == StompCommand.CONNECT) {

            String accessToken = (String) accessor.getHeader(ACCESS_AUTHORIZATION);

            try {
                jwtUtils.verifyToken(accessToken);

            } catch (TokenExpiredException e) {
                log.debug("Access Token is Expired");
                throw new RestBusinessException.Failure(AuthenticationErrorCode.INVALID_TOKEN);

            } catch (JWTVerificationException e) {
                log.error("Access Token is Invalid");
                throw new RestBusinessException.Failure(AuthenticationErrorCode.INVALID_TOKEN);
            }
        }
        return message;
    }

}
