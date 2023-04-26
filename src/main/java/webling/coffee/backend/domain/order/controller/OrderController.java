package webling.coffee.backend.domain.order.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import webling.coffee.backend.domain.order.dto.request.OrderRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webling.coffee.backend.domain.order.dto.response.OrderResponseDto;
import webling.coffee.backend.domain.order.service.OrderFacade;
import webling.coffee.backend.global.annotation.AuthRequired;
import webling.coffee.backend.global.annotation.AuthUser;
import webling.coffee.backend.global.context.UserAuthentication;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderFacade orderFacade;

    @Operation(
            summary = "주문생성",
            description = """
                    ## [주문 생성 API]
                    ### 주문을 생성합니다.
                    ###
                    
                    ## [호출 권한]
                    ### ALL
                    
                    ## [Exceptions]
                    ### 
                    """,
            externalDocs = @ExternalDocumentation(description = "ENUM 정보", url = "https://www.notion.so/API-ENUM-c65d84ea50a249dd972d7c8c296750ee")
    )
    @AuthRequired
    @PostMapping("")
    public ResponseEntity<OrderResponseDto.Create> createOrder (final @AuthUser UserAuthentication authentication,
                                                                final @NotNull OrderRequestDto.Create request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderFacade.create(authentication.getUserId(), request));
    }
}
