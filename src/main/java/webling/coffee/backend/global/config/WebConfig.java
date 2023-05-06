package webling.coffee.backend.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import webling.coffee.backend.global.interceptors.AuthenticationInterceptor;
import webling.coffee.backend.global.interceptors.RequestLogInterceptor;
import webling.coffee.backend.global.resolver.AuthenticationArgumentResolver;

import java.util.List;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.GET;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final RequestLogInterceptor requestLogInterceptor;
    private final AuthenticationInterceptor authenticationInterceptor;
    private final AuthenticationArgumentResolver authenticationArgumentResolver;

    @Value("${cors.swagger}")
    private String backendUrl;

    @Value("${cors.frontend-local}")
    private String frontendLocalUrl;

    @Bean
    public PasswordEncoder passwordEncoder () {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLogInterceptor);
        registry.addInterceptor(authenticationInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationArgumentResolver);
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(backendUrl, frontendLocalUrl)
                .allowedMethods(GET.name(), POST.name(), PUT.name(), DELETE.name(), PATCH.name())
                .allowedHeaders("*");
    }
}
