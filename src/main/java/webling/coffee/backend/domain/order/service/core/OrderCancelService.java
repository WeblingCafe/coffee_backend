package webling.coffee.backend.domain.order.service.core;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.order.dto.request.OrderRequestDto;
import webling.coffee.backend.domain.order.entity.Order;
import webling.coffee.backend.domain.order.entity.OrderCancel;
import webling.coffee.backend.domain.order.repository.orderCancel.OrderCancelRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderCancelService {

    private final OrderCancelRepository orderCancelRepository;

    public void saveCancelMessage(final @NotNull Order order, final @NotNull OrderRequestDto.Cancel request) {
        orderCancelRepository.save(OrderCancel.toEntity(order, request));
    }
}
