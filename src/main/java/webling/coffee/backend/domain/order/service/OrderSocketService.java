package webling.coffee.backend.domain.order.service;

import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Service;

@ServerEndpoint(value = "/v1/order/socket")
@Service
public class OrderSocketService {
}
