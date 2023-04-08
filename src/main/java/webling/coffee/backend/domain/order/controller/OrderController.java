package webling.coffee.backend.domain.order.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import webling.coffee.backend.domain.order.dto.request.OrderRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webling.coffee.backend.domain.order.dto.response.OrderResponseDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/orders")
public class OrderController {

    @Operation(
            summary = "주문생성",
            description = """
                    # [Request Body]
                    
                    """,
            externalDocs = @ExternalDocumentation(description = "ENUM 정보", url = "https://www.notion.so/API-ENUM-c65d84ea50a249dd972d7c8c296750ee")
    )
    @PostMapping("")
    public ResponseEntity<OrderResponseDto.Create> createOrder (final @NotNull OrderRequestDto.Create request) {

        return null;
    }
}
