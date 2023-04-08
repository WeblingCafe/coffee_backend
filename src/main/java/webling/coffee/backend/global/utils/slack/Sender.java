package webling.coffee.backend.global.utils.slack;

import com.google.gson.Gson;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Sender {

    public void sendSlack(final @NotBlank String messages,
                          final @NotBlank String webhookUrl) {

        String payload = new Gson().toJson(new Message(messages));

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(payload, headers);

        restTemplate.postForEntity(webhookUrl, entity, String.class);
    }
}

