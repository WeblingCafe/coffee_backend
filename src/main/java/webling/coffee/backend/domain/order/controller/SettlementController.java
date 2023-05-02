package webling.coffee.backend.domain.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
                    
                    ## [호출 권한]
                    ### MANAGER, BARISTA, DEVELOPER
                    """
    )
    @AuthRequired (roles = {MANAGER, BARISTA, DEVELOPER})
    @GetMapping("")
    public ResponseEntity<List<SettlementResponseDto.User>> settlementAll () {
        return ResponseEntity.ok()
                .body(settlementFacade.settlementAll());
    }
}
