package webling.coffee.backend.domain.order.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final SimpMessageSendingOperations messagingTemplate;

//    public void alarmByMessage(MessageDto messageDto) {
//        messagingTemplate.convertAndSend("/sub/" + messageDto.getReceiverId(), messageDto);
//    }

}

