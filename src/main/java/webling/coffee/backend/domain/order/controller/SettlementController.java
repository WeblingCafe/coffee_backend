package webling.coffee.backend.domain.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webling.coffee.backend.domain.order.dto.request.SettlementRequestDto;
import webling.coffee.backend.domain.order.dto.response.SettlementResponseDto;
import webling.coffee.backend.domain.order.service.SettlementFacade;
import webling.coffee.backend.global.annotation.AuthRequired;

import java.util.List;

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
                    ### 모든 회원의 장바구니와 주문 정보를 보여줍니다.
                    ### 검색조건은 다음과 같습니다.
                    ### - 유저의 본명 (username), 유저의 닉네임 (nickname), 주문 내역의 기간
                    ### - 주문 내역의 기간 정보가 없을 경우 지금으로부터 15일 전 주문부터 현재까지를 기본으로 조회합니다.
                    
                    ## [호출 권한]
                    ### MANAGER, BARISTA, DEVELOPER
                    """
    )
    @AuthRequired (roles = {MANAGER, BARISTA, DEVELOPER})
    @PostMapping("")
    public ResponseEntity<List<SettlementResponseDto.User>> settlementAllBySearchOptions(final @NotNull @RequestBody SettlementRequestDto.Search request) {
        return ResponseEntity.ok()
                .body(settlementFacade.settlementAllBySearchOptions(request));
    }
}
