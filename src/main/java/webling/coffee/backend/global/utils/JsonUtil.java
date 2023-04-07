package webling.coffee.backend.global.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonUtil {

    public static String marshallingJson(final Object paramValue) throws Exception {

        return parseJson(paramValue);
    }

    private static String parseJson(Object paramValue) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        DefaultSerializerProvider provider = new DefaultSerializerProvider.Impl();
        objectMapper.setSerializerProvider(provider);

        return objectMapper.writeValueAsString(paramValue);
    }
}
