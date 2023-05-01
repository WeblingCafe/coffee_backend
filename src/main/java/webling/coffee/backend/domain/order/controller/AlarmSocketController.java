package webling.coffee.backend.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AlarmSocketController {

    private final SimpMessageSendingOperations messageTemplate;

    @GetMapping("/alarm/coffee")
    public String alarmTest() {
        return "/stomp";
    }

    @MessageMapping("/{userId}")
    public void message(@DestinationVariable("userId") Long userId) {
        messageTemplate.convertAndSend("/sub/" + userId, "alarm socket connection completed.");
    }

}
