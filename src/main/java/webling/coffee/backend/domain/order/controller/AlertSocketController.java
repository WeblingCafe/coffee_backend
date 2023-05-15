package webling.coffee.backend.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import webling.coffee.backend.domain.order.service.OrderFacade;

@Controller
@RequiredArgsConstructor
public class AlertSocketController {

    private final OrderFacade orderFacade;



}
