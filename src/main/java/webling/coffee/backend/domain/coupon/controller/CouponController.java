package webling.coffee.backend.domain.coupon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webling.coffee.backend.domain.coupon.dto.request.CouponRequestDto;
import webling.coffee.backend.domain.coupon.service.CouponFacade;
import webling.coffee.backend.global.annotation.AuthRequired;
import webling.coffee.backend.global.annotation.AuthUser;
import webling.coffee.backend.global.context.UserAuthentication;
import webling.coffee.backend.global.responses.success.codes.CouponSuccessCode;
import webling.coffee.backend.global.responses.success.response.SuccessResponse;

import static webling.coffee.backend.global.enums.UserRole.DEVELOPER;
import static webling.coffee.backend.global.enums.UserRole.MANAGER;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/coupons")
@RestController
public class CouponController {

    private final CouponFacade couponFacade;

    @Operation(
            summary = "쿠폰 부여",
            description =
                    """
                    ## [쿠폰 부여 API]
                    ### 쿠폰을 가입된 회원에게 부여합니다.
                    
                    ## [호출 권한]
                    ### MANAGER, DEVELOPER
                    
                    ## [Exceptions]
                    ### 1. UserErrorCode.NOT_FOUND : 쿠폰을 발급할 유저를 찾을 수 없을 경우 해당 예외를 리턴합니다.
                    ### 2. EnumValueErrorCode.COUPON_TYPE_VALUE_INVALID : 쿠폰 타입의 ENUM 값을 찾을 수 없을 경우 해당 예외를 리턴합니다.
                    """
    )
    @AuthRequired(roles = {MANAGER, DEVELOPER})
    @PostMapping("/{userId}")
    public ResponseEntity<SuccessResponse> create (final @AuthUser @Parameter(hidden = true) UserAuthentication authentication,
                                                   final @NotNull @PathVariable Long userId,
                                                   final @NotNull @RequestBody CouponRequestDto.Create request) {

        return SuccessResponse.toResponseEntity(
                CouponSuccessCode.CREATE,
                couponFacade.create(authentication.getEmail(), userId, request));
    }

    @Operation(
            summary = "내 쿠폰 조회",
            description =
                    """
                    ## [내 쿠폰 조회 API]
                    ### 현재 로그인한 유저가 소유한 쿠폰을 조회합니다.
                    ### pathVariable 에 따라 조회되는 상태를 구분합니다.
                    ### - available : 사용가능한 쿠폰만 조회, expired : 만료된 쿠폰만 조회, all : 모두 조회
                    ### - status 에 정해진 값이 아닌 다른 값이 들어온다면 기본적으로 모두 조회됩니다.
                    
                    ## [호출 권한]
                    ### ALL
                    
                    ## [Exceptions]
                    """
    )
    @AuthRequired
    @GetMapping("/me/{status}")
    public ResponseEntity <SuccessResponse> findAllByMeOnStatus (final @AuthUser @Parameter(hidden = true) UserAuthentication authentication,
                                                                              final @PathVariable (required = false) String status) {

        return SuccessResponse.toResponseEntity(
                CouponSuccessCode.FIND,
                couponFacade.findAllByMeOnStatus(authentication.getUserId(), status));
    }
}
