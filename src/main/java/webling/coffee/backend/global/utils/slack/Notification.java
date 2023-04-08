package webling.coffee.backend.global.utils.slack;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Notification {

    private final Sender sender;

    public void sendNotification (final @NotBlank String brandName,
                                  final @NotBlank String title,
                                  final @NotBlank String content,
                                  final @NotBlank String webhookUrl) {

        sender.sendSlack(createMessages(brandName, title, content), webhookUrl);
    }

    private String createMessages(final @NotBlank String brandName,
                                  final @NotBlank String title,
                                  final @NotBlank String content) {

        StringBuilder message = new StringBuilder();

        return message.append("[").append(brandName).append("]")
                .append(title).append("\n")
                .append(content)
                .toString();
    }
}
