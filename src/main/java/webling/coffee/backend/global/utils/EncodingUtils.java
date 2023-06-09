package webling.coffee.backend.global.utils;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class EncodingUtils implements InitializingBean {

    private static PasswordEncoder encoder;

    private final PasswordEncoder beanEncoder;

    @Override
    public void afterPropertiesSet() {
        encoder = this.beanEncoder;
    }

    public static String encode(final @NotBlank String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public static boolean isNotMatches(final @NotBlank String rawPassword,
                                       final @NotBlank String encodePassword) {
        return !encoder.matches(rawPassword, encodePassword);
    }
}
