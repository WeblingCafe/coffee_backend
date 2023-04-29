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

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderFacade orderFacade;

    @Operation(
            summary = "주문 결제",
            description = """
                    ## [주문 결제 API]
                    ### 주문을 생성하고 결제합니다.
                    ### 주문 정보를 리스트로 받습니다.
                    
                    ## [주문 생성 description]
                    ### 주문이 저장될 때, 다음의 로직들이 하나의 트랜잭션으로 작동합니다.
                    ### 1. 주문당 사용하는 쿠폰 수량과 계정이 소유한 쿠폰 수량을 비교하여 쿠폰을 사용합니다. (isAvailable false 로 변경)
                    ### 2. 요청 정보를 바탕으로 주문정보를 생성합니다. 이때, 한 주문의 총 비용을 계산합니다.
                    ### 3. 주문정보가 생성이 되면, 장바구니를 생성합니다. 이때, 주문들이 담긴 장바구니의 총 비용을 계산합니다.
                    ### 4. 사용한 쿠폰 만큼의 수량을 제외한 만큼 사용자의 스탬프 개수를 추가합니다.
                    ### 5. 추가된 스탬프 개수가 20개를 넘을 시, COMMON 쿠폰을 유저에게 발급하고, 스탬프 개수를 차감합니다.
                    
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
    public ResponseEntity<List<OrderResponseDto.Create>> createOrder (final @AuthUser @Parameter(hidden = true) UserAuthentication authentication,
                                                                final @NotNull @RequestBody OrderRequestDto.Cart request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderFacade.create(authentication.getUserId(), request));
    }
}
