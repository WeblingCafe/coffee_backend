package webling.coffee.backend.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import webling.coffee.backend.domain.order.dto.request.OrderRequestDto;
import webling.coffee.backend.domain.order.service.OrderFacade;
import webling.coffee.backend.global.responses.success.codes.OrderSuccessCode;
import webling.coffee.backend.global.responses.success.response.SuccessResponse;

@Controller
@RequiredArgsConstructor
public class OrderAlertController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final OrderFacade orderFacade;

    /**
     * 주문 들어갈 때 바리스타에게 웹소켓 통해 주문 생성 알림
     */

    @MessageMapping("/alert/order")
    @SendTo("/topic/barista")
    public ResponseEntity<SuccessResponse> orderAlert (@Payload OrderRequestDto.OrderCreateAlert request) {


        return SuccessResponse.toResponseEntity(
                OrderSuccessCode.CREATE
        );
    }

    /**
     * 주문 완료시 유저에게 웹소켓 통한 알림
     * @param userId
     */
    @MessageMapping("/alert/complete/{userId}/order/{orderId}")
    public void orderCompleted(@DestinationVariable("userId") Long userId,
                               @DestinationVariable("orderId") Long orderId) {
        messagingTemplate.convertAndSend("/topic/" + userId, "alarm socket connection completed.");
    }

    /**
     * 주문 취소 (by 바리스타) 유저에게 웹소켓 통한 알림
     * @param userId
     */
    @MessageMapping("/alert/cancel/{userId}/order/{orderId}")
    public void orderCancelByBarista(@DestinationVariable("userId") Long userId,
                                     @DestinationVariable("orderId") Long orderId) {
        messagingTemplate.convertAndSend("/topic/" + userId, "alarm socket connection completed.");
    }

    /**
     * 주문 취소 (by 유저) 바리스타에게 웹소켓 통한 알림
     */
    @MessageMapping("/alert/order/me/cancel")
    @SendTo("/topic/barista")
    public void orderCancelByUser() {
    }

}
