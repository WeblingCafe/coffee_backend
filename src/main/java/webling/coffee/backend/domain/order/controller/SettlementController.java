package webling.coffee.backend.domain.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webling.coffee.backend.domain.order.dto.request.SettlementRequestDto;
import webling.coffee.backend.domain.order.service.SettlementFacade;
import webling.coffee.backend.global.annotation.AuthRequired;
import webling.coffee.backend.global.annotation.AuthUser;
import webling.coffee.backend.global.context.UserAuthentication;
import webling.coffee.backend.global.responses.success.codes.OrderSuccessCode;
import webling.coffee.backend.global.responses.success.response.SuccessResponse;

import static webling.coffee.backend.global.enums.UserRole.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/settlement")
public class SettlementController {

    private final SettlementFacade settlementFacade;

    @Operation(
            summary = "모든 회원 정산 API",
            description = """
                    ## [모든 회원 정산 API]
                    ### 모든 회원의 장바구니와 주문 정보를 보여줍니다. 주문이 완료된 주문 건만 보여줍니다. (COMPLETED 상태)
                    ### 검색조건은 다음과 같습니다.
                    ### - 유저의 본명 (username), 유저의 닉네임 (nickname), 주문 내역의 기간
                    ### - 주문 내역의 fromDate 가 null 인 경우 15일 전을 기본값으로 조회합니다.
                    ### - 주문 내역의 toDate 가 null 인 경우 지금 시점을 기본값으로 조회합니다.
                    ### - fromDate 와 toDate 가 모두 null 인 경우 현재시점과 15일전 사이의 모든 내역을 조회합니다.
                    
                    ## [호출 권한]
                    ### MANAGER, BARISTA, DEVELOPER
                    """
    )
    @AuthRequired (roles = {MANAGER, BARISTA, DEVELOPER})
    @GetMapping("")
    public ResponseEntity<SuccessResponse> settlementAllBySearchOptions(final @NotNull @ModelAttribute SettlementRequestDto.Search request) {
        return SuccessResponse.toResponseEntity(
                OrderSuccessCode.SETTLEMENT,
                settlementFacade.settlementAllBySearchOptions(request)
        );
    }

    @Operation(
            summary = "내 정산 내역 조회 API",
            description = """
                    ## [내 정산 내역 조회 API]
                    ### 나의 장바구니와 주문 정보를 보여줍니다. 주문이 완료된 주문 건만 보여줍니다. (COMPLETED 상태)
                    ### 검색조건은 다음과 같습니다.
                    ### 주문내역의 기간
                    ### - 주문 내역의 fromDate 가 null 인 경우 15일 전을 기본값으로 조회합니다.
                    ### - 주문 내역의 toDate 가 null 인 경우 지금 시점을 기본값으로 조회합니다.
                    ### - fromDate 와 toDate 가 모두 null 인 경우 현재시점과 15일전 사이의 모든 내역을 조회합니다.
                    
                    ## [호출 권한]
                    ### ALL
                    
                    ## [Exceptions]
                    ### UserErrorCode.NOT_FOUND : 현재 로그인한 유저 정보를 찾지 못할 경우 해당 예외를 리턴합니다.
                    """
    )
    @AuthRequired
    @GetMapping("/me")
    public ResponseEntity<SuccessResponse> settlementMeBySearchOptions (final @NotNull @Parameter(hidden = true) @AuthUser UserAuthentication authentication,
                                                                                   final @NotNull @RequestBody SettlementRequestDto.BaseField request) {

        return SuccessResponse.toResponseEntity(
                OrderSuccessCode.SETTLEMENT,
                settlementFacade.settlementMeBySearchOptions(authentication.getUserId(), request)
        );
    }
}
