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
public class SwaggerConfig {

    @Value("${swagger.servers.domain.local}")
    private String localDomain;

    @Value("${swagger.servers.domain.prd}")
    private String prdDomain;

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("Users")
                .pathsToMatch("/v1/users/**")
                .build();
    }

    @Bean
    public GroupedOpenApi orderApi() {
        return GroupedOpenApi.builder()
                .group("Orders")
                .pathsToMatch("/v1/orders/**")
                .build();
    }

    @Bean
    public GroupedOpenApi couponApi() {
        return GroupedOpenApi.builder()
                .group("Coupons")
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
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Progressive Production Processing Documentation")
                        .url("https://springshop.wiki.github.org/docs"));

        openAPI.setServers(Arrays.asList(local, prd));

        return openAPI;
    }
}
