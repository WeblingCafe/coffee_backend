package webling.coffee.backend.domain.authentication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import webling.coffee.backend.domain.user.dto.request.UserRequestDto;
import webling.coffee.backend.global.responses.success.codes.AuthSuccessCode;
import webling.coffee.backend.global.responses.success.response.SuccessResponse;

@Controller
@RequiredArgsConstructor
public class UserAlertController {

    @MessageMapping("/alert/login")
    @SendTo("/topic/public")
    public ResponseEntity<SuccessResponse> loginAlert (@Payload UserRequestDto.LoginAlert request,
                                                       SimpMessageHeaderAccessor accessor) {
        accessor.getSessionAttributes().put("userEmail", request.getUserEmail());
        return SuccessResponse.toResponseEntity(
                AuthSuccessCode.LOGIN
        );
    }
}
