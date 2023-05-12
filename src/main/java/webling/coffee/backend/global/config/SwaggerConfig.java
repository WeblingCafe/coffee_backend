package webling.coffee.backend.global.config;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

import static webling.coffee.backend.global.utils.JwtUtils.ACCESS_AUTHORIZATION;
import static webling.coffee.backend.global.utils.JwtUtils.REFRESH_AUTHORIZATION;

@Configuration
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
                .addOperationCustomizer(operationCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi orderApi() {
        return GroupedOpenApi.builder()
                .group("주문 API")
                .pathsToMatch("/v1/orders/**")
                .addOperationCustomizer(operationCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi settlementApi() {
        return GroupedOpenApi.builder()
                .group("정산 API")
                .pathsToMatch("/v1/settlement/**")
                .addOperationCustomizer(operationCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi menuApi() {
        return GroupedOpenApi.builder()
                .group("메뉴 API")
                .pathsToMatch("/v1/menus/**", "/v1/categories/**")
                .addOperationCustomizer(operationCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi couponApi() {
        return GroupedOpenApi.builder()
                .group("쿠폰 API")
                .pathsToMatch("/v1/coupons/**")
                .addOperationCustomizer(operationCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi boardApi() {
        return GroupedOpenApi.builder()
                .group("게시판 API")
                .pathsToMatch("/v1/boards/**")
                .addOperationCustomizer(operationCustomizer())
                .build();
    }

    @Bean
    public OperationCustomizer operationCustomizer () {
        return (operation, handlerMethod) -> {
            Parameter accessAuthorization = new Parameter()
                    .in(ParameterIn.HEADER.toString())
                    .name(ACCESS_AUTHORIZATION)
                    .required(true);

            operation.addParametersItem(accessAuthorization);
            return operation;
        };
    }

    @Bean
    @Profile("local")
    public OpenAPI springShopOpenLocalAPI() {
        OpenAPI openApi = getOpenApi();

        Server local = new Server();
        local.setUrl(localDomain);

        openApi.setServers(List.of(local));

        return openApi;
    }

    @Bean
    @Profile("prd")
    public OpenAPI springShopOpenPrdAPI() {
        OpenAPI openApi = getOpenApi();

        Server prd = new Server();
        prd.setUrl(prdDomain);

        openApi.setServers(List.of(prd));

        return openApi;
    }

    private OpenAPI getOpenApi (){
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
                        .url("https://github.com/WeblingCafe/coffee_backend"));

        return openAPI;
    }
}
