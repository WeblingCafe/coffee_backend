package webling.coffee.backend.global.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
//@SecuritySchemes(
//        {
//                @SecurityScheme(
//                        name = "Bearer Authentication",
//                        type = SecuritySchemeType.HTTP,
//                        bearerFormat = "JWT",
//                        scheme = "bearer"
//                ),
//                @SecurityScheme(
//                        name = "Bearer Refresh Authentication",
//                        paramName = "Refresh-Token",
//                        type = SecuritySchemeType.HTTP,
//                        bearerFormat = "JWT",
//                        scheme = "bearer"
//                )
//        }
//)
public class SwaggerConfig {

    @Value("${swagger.servers.domain.local}")
    private String localDomain;

    @Value("${swagger.servers.domain.prd}")
    private String prdDomain;

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("권환 API")
                .pathsToMatch("/v1/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("사용자 API")
                .pathsToMatch("/v1/users/**")
                .build();
    }

    @Bean
    public GroupedOpenApi orderApi() {
        return GroupedOpenApi.builder()
                .group("주문 API")
                .pathsToMatch("/v1/orders/**")
                .build();
    }

    @Bean
    public GroupedOpenApi menuApi() {
        return GroupedOpenApi.builder()
                .group("메뉴 API")
                .pathsToMatch("/v1/menus/**")
                .build();
    }

    @Bean
    public GroupedOpenApi couponApi() {
        return GroupedOpenApi.builder()
                .group("쿠폰 API")
                .pathsToMatch("/v1/coupons/**")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {

        Server local = new Server();
        local.setUrl(localDomain);

        Server prd = new Server();
        prd.setUrl(prdDomain);

        OpenAPI openAPI = new OpenAPI()
                .info(new Info().title("Webling Coffee API")
                        .description("Webling Coffee Application Backend API")
                        .version("v1.0")
                        .license(new License().name("Springdoc").url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Webling Coffee Application Backend Swagger Documentation")
                        .url("https://github.com/mingj7235/coffee_backend"));

        openAPI.setServers(Arrays.asList(local, prd));

        return openAPI;
    }
}
