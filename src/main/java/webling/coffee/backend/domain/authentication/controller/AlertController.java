package webling.coffee.backend.domain.authentication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import webling.coffee.backend.domain.user.dto.request.UserRequestDto;
import webling.coffee.backend.global.responses.success.codes.AuthSuccessCode;
import webling.coffee.backend.global.responses.success.response.SuccessResponse;

import static webling.coffee.backend.global.utils.JwtUtils.ACCESS_AUTHORIZATION;

@Controller
@RequiredArgsConstructor
public class AlertController {

    @MessageMapping("/alert.loginUser")
    @SendTo("/topic/public")
    public ResponseEntity<SuccessResponse> loginAlert (@RequestHeader(name = ACCESS_AUTHORIZATION) String accessToken,
                                                       @Payload UserRequestDto.LoginAlert request,
                                                       SimpMessageHeaderAccessor accessor) {
        accessor.setHeader(ACCESS_AUTHORIZATION, accessToken);
        accessor.getSessionAttributes().put("userEmail", request.getUserEmail());
        return SuccessResponse.toResponseEntity(
                AuthSuccessCode.LOGIN
        );
    }
}
