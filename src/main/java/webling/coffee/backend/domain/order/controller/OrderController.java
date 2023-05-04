package webling.coffee.backend.domain.order.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webling.coffee.backend.domain.order.dto.request.OrderRequestDto;
import webling.coffee.backend.domain.order.dto.response.OrderResponseDto;
import webling.coffee.backend.domain.order.service.OrderFacade;
import webling.coffee.backend.global.annotation.AuthRequired;
import webling.coffee.backend.global.annotation.AuthUser;
import webling.coffee.backend.global.context.UserAuthentication;
import webling.coffee.backend.global.enums.UserRole;

import java.util.List;

import static webling.coffee.backend.global.enums.UserRole.BARISTA;
import static webling.coffee.backend.global.enums.UserRole.DEVELOPER;

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
                    ### 주문을 생성하고 장바구니 단계에서 결제합니다.
                    ### 주문들은 리스트로 생성이 되며, 하나의 장바구니에 담기고 결제 할 때 쿠폰을 얼마나 쓸지 결정합니다.
                    
                    ## [주문 생성 description]
                    ### 주문이 저장될 때, 다음의 로직들이 하나의 트랜잭션으로 작동합니다.
                    ### 1. 요청 정보를 바탕으로 주문정보를 생성합니다. 이때, 한 주문의 총 비용을 계산합니다.
                    ### 2. 주문정보가 생성이 되면, 장바구니를 생성합니다. 이때, 주문들이 담긴 장바구니의 총 비용을 계산합니다.
                    ### 3. 주문 리스트가 담긴 장바구니 단계에서 쿠폰 수량과 계정이 소유한 쿠폰 수량을 비교하여 쿠폰을 사용합니다. (isAvailable false 로 변경)
                    ### 4. 사용한 쿠폰을 제외한 최종 금액이 장바구니 테이블에 저장됩니다. 이때 원래 주문 가격, 사용한 쿠폰 수량, 총 주문 가격이 계산되어 장바구니 테이블에 저장됩니다.
                    ### 5. 사용한 쿠폰 만큼의 수량을 제외한 만큼 사용자의 스탬프 개수를 추가합니다.
                    ### 6. 추가된 스탬프 개수가 20개를 넘을 시, COMMON 쿠폰을 유저에게 발급하고, 스탬프 개수를 차감합니다.
                    
                    ## [호출 권한]
                    ### ALL
                    
                    ## [Exceptions]
                    ### 1. CouponErrorCode.EXCEEDED_AMOUNT : 사용 요청한 쿠폰 개수가 해당 USER 가 소유한 쿠폰 개수를 초과했을 시 해당 예외를 리턴합니다.
                    ### 2. UserErrorCode.NOT_FOUND : 주문하는 USER 나, 받는 USER 를 찾을 수 없을 경우 해당 예외를 리턴합니다.
                    ### 3. MenuErrorCode.NOT_FOUND : 현재 판매 가능한 메뉴 중 주문 하려는 메뉴를 찾을 수 없을 경우 해당 예외를 리턴합니다.
                    ### 4. MenuErrorCode.COLD_NOT_AVAILABLE : 주문하는 메뉴가 cold 가 불가능한데, 주문을 cold 로 한 경우 예외를 리턴합니다.
                    ### 5. MenuErrorCode.HOT_NOT_AVAILABLE : 주문하는 메뉴가 hot 이 불가능한데, 주문을 hot 으로 한 경우 예외를 리턴합니다.
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

    @Operation(
            summary = "주문된 주문 리스트 조회",
            description =
                    """
                    ## [주문된 주문 리스트 조회 API]
                    ### 주문 상태가 ORDERED 인 모든 주문정보를 조회합니다.
                    
                    ## [호출 권한]
                    ### BARISTA, DEVELOPER
                    
                    ## [Exceptions]
                    """
    )
    @AuthRequired (roles = {BARISTA, DEVELOPER})
    @GetMapping ("")
    public ResponseEntity<List<OrderResponseDto.Find>> findOrderedAll () {
        return ResponseEntity.ok()
                .body(orderFacade.findOrderedAll());
    }

    @Operation(
            summary = "내가 주문된 주문 리스트 조회",
            description =
                    """
                    ## [내가 주문된 주문 리스트 조회 API]
                    ### 로그인한 회원의 주문중 주문 상태가 ORDERED 인 모든 주문정보를 조회합니다.
                    
                    ## [호출 권한]
                    ### ALL
                    
                    ## [Exceptions]
                    """
    )
    @AuthRequired
    @GetMapping ("/me")
    public ResponseEntity<List<OrderResponseDto.Find>> findMeOrderedAll (final @NotNull @AuthUser @Parameter (hidden = true) UserAuthentication authentication) {
        return ResponseEntity.ok()
                .body(orderFacade.findMeOrderedAll(authentication.getUserId()));
    }
}
