package webling.coffee.backend.domain.order.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.coupon.service.core.CouponService;
import webling.coffee.backend.domain.menu.service.core.MenuService;
import webling.coffee.backend.domain.order.dto.request.OrderRequestDto;
import webling.coffee.backend.domain.order.dto.response.OrderResponseDto;
import webling.coffee.backend.domain.order.entity.Order;
import webling.coffee.backend.domain.order.entity.OrderCart;
import webling.coffee.backend.domain.order.service.core.OrderCancelService;
import webling.coffee.backend.domain.order.service.core.OrderCartService;
import webling.coffee.backend.domain.order.service.core.OrderService;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.domain.user.service.core.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final UserService userService;
    private final OrderService orderService;
    private final OrderCartService orderCartService;
    private final OrderCancelService orderCancelService;
    private final MenuService menuService;
    private final CouponService couponService;

    @Transactional
    public List<OrderResponseDto.Create> create(final @NotNull Long userId,
                                          final @NotNull OrderRequestDto.Cart request) {

        User user = userService.findById(userId);
        List<Order> orderEntityList = new ArrayList<>();

        for (OrderRequestDto.Create orderRequest : request.getOrderList()) {

            Order orderEntity = orderService.create(
                    user,
                    userService.findById(orderRequest.getRecipientId()),
                    menuService.findByIdAndAvailable(orderRequest.getMenuId()),
                    orderRequest);

            orderEntityList.add(orderEntity);
        }

        Long couponAmount = couponService.useCoupons(couponService.findAllByUserAndIsAvailable(user), request.getCouponAmount());
        OrderCart cart = orderCartService.save(orderEntityList, user, couponAmount);

        orderService.addCart(cart, orderEntityList);

        userService.addStamps(user, cart.getTotalPrice());

        couponService.issueCouponByStamp(user);

        return orderEntityList.stream()
                .map(OrderResponseDto.Create::toDto)
                .collect(Collectors.toList());
    }

    @Transactional (readOnly = true)
    public List<OrderResponseDto.Find> findOrderedAll() {
        return orderService.findOrderedAll()
                .stream()
                .map(OrderResponseDto.Find::toDto)
                .collect(Collectors.toList());
    }

    @Transactional (readOnly = true)
    public List<OrderResponseDto.Find> findMeOrderedAll(final @NotNull Long userId) {
        return orderService.findMeOrderedAll(userId)
                .stream()
                .map(OrderResponseDto.Find::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponseDto.Cancel cancelOrder(final @NotNull Long orderId,
                                               final @NotNull OrderRequestDto.Cancel request) {

        Order order = orderService.findByOrderIdAndOrdered(orderId);

        // 동시성 이슈 해결 해야함 (사용자가 주문하고 취소하는 시점에서 바리스타도 주문된 주문인줄알고 취소하는 경우)
        orderService.cancelOrder(order);
        orderCancelService.saveCancelMessage(order, request);

        /**
         * orderCart 의 정보를 조회
         * orderCart 의 totalPrice, couponAmount, 해당 order 의 totalPrice 가 필요
         * 1. couponAmount 가 0 인경우
         * -> 그냥 order 만 cancel 하면 됨
         * 2. coupon 재지급, orderCart 의 couponAmount 0, totalPrice 재계산
         *
          */

        OrderCart orderCart = order.getOrderCart();
        if (orderCart.getUsedCouponAmount() > 0) {
            couponService.refundCoupon(order.getUser(), orderCart.getUsedCouponAmount());
            orderCartService.refundOrder(orderCart);
        }

        // stamp 는 취소 수량만큼 - 시키기. stamp 값에 - 가 들어갈 수 있음 (- 인 경우 프론트단의 작업 필요)

        // dto 리턴

        return null;
    }

}














