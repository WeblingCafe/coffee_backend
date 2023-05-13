package webling.coffee.backend.domain.order.dto.request;

import lombok.Getter;
import lombok.Setter;
import webling.coffee.backend.global.enums.MessageType;

@Getter
@Setter
public class ChatMessage {

    private MessageType type;
    private String content;
    private String sender;
}
