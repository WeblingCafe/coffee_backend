package webling.coffee.backend.domain.order.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webling.coffee.backend.domain.order.dto.request.OrderRequestDto;
import webling.coffee.backend.domain.order.dto.response.OrderResponseDto;
import webling.coffee.backend.domain.order.service.OrderFacade;
import webling.coffee.backend.global.annotation.AuthRequired;
import webling.coffee.backend.global.annotation.AuthUser;
import webling.coffee.backend.global.context.UserAuthentication;

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
                    
                    ## [호출 권한]
                    ### ALL
                    
                    ## [Exceptions]
                    ### 1. CouponErrorCode.EXCEEDED_AMOUNT : 사용 요청한 쿠폰 개수가 해당 USER 가 소유한 쿠폰 개수를 초과했을 시 해당 예외를 리턴합니다.
                    ### 2. UserErrorCode.NOT_FOUND : 주문하는 USER 나, 받는 USER 를 찾을 수 없을 경우 해당 예외를 리턴합니다.
                    ### 3. MenuErrorCode.NOT_FOUND : 현재 판매 가능한 메뉴 중 주문 하려는 메뉴를 찾을 수 없을 경우 해당 예외를 리턴합니다.
                    """,
            externalDocs = @ExternalDocumentation(description = "ENUM 정보", url = "https://www.notion.so/API-ENUM-c65d84ea50a249dd972d7c8c296750ee")
    )
    @AuthRequired
    @PostMapping("")
    public ResponseEntity<OrderResponseDto.Create> createOrder (final @AuthUser @Parameter(hidden = true) UserAuthentication authentication,
                                                                final @NotNull @RequestBody OrderRequestDto.Create request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderFacade.create(authentication.getUserId(), request));
    }
}
