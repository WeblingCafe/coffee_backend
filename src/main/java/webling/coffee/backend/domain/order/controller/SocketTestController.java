package webling.coffee.backend.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import webling.coffee.backend.domain.order.dto.request.ChatMessage;
import webling.coffee.backend.domain.order.service.OrderFacade;

@Controller
@RequiredArgsConstructor
public class SocketTestController {

    /**
     * 주문이 들어오면 -> order 가 추가되면 chat message 에 변경이 일어나도록 request 없이! 로직을 짜볼것
     */
    private final OrderFacade orderFacade;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}
