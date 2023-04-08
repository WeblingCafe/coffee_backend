package webling.coffee.backend.global.utils.slack;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

    private String text;
    public Message(String text) {
        this.text = text;
    }
}
