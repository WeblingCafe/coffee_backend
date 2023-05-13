package webling.coffee.backend.domain.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import webling.coffee.backend.domain.order.dto.request.ChatMessage;
import webling.coffee.backend.global.enums.MessageType;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionConnectedEvent event) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info(String.format("%s Disconnected", username));

            ChatMessage message = new ChatMessage();
            message.setType(MessageType.LEAVE);
            message.setSender(username);

            messagingTemplate.convertAndSend("/topic/public", message);
        }

    }


}
