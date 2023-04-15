package webling.coffee.backend.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import webling.coffee.backend.global.interceptors.AuthenticationInterceptor;
import webling.coffee.backend.global.interceptors.RequestLogInterceptor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final RequestLogInterceptor requestLogInterceptor;

    private final AuthenticationInterceptor authenticationInterceptor;

    @Bean
    public PasswordEncoder passwordEncoder () {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLogInterceptor);
        registry.addInterceptor(authenticationInterceptor);

    }
}
