package webling.coffee.backend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebConfig {

    @Bean
    public PasswordEncoder passwordEncoder () {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
