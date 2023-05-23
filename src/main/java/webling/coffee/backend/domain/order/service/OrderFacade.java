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
import webling.coffee.backend.domain.order.entity.OrderCancel;
import webling.coffee.backend.domain.order.entity.OrderCart;
import webling.coffee.backend.domain.order.service.core.OrderCancelService;
import webling.coffee.backend.domain.order.service.core.OrderCartService;
import webling.coffee.backend.domain.order.service.core.OrderService;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.domain.user.service.core.UserService;

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
        List<Order> orderEntityList = createOrderList(user, request);

        Long couponAmount = couponService.useCoupons(couponService.findAllByUserAndIsAvailable(user), request.getCouponAmount());
        OrderCart cart = orderCartService.save(orderEntityList, user, couponAmount);

        orderService.addCart(cart, orderEntityList);
        userService.addStamps(user, cart.getTotalPrice());
        couponService.issueCouponByStamp(user);

        return orderEntityList.stream()
                .map(OrderResponseDto.Create::toDto)
                .collect(Collectors.toList());
    }

    private List<Order> createOrderList (final @NotNull User user,
                                         final @NotNull OrderRequestDto.Cart request) {

        return request.getOrderList().stream()
                .map(orderRequest ->
                        orderService.create(
                            user,
                            userService.findById(orderRequest.getRecipientId()),
                            menuService.findByIdAndAvailable(orderRequest.getMenuId()),
                            orderRequest)
                )
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

        return cancelOrderProcess(
                orderService.findByOrderIdAndOrderedFetchUserAndOrderCart(orderId),
                request);
    }

    @Transactional
    public OrderResponseDto.Cancel cancelOrder(Long userId, Long orderId, OrderRequestDto.Cancel request) {
        return cancelOrderProcess(
                orderService.findByUserIdAndOrderIdAndOrderedFetchUserAndOrderCart(userId, orderId),
                request);
    }

    private void refundUsedCouponAmount(Order orderToCancel) {
        OrderCart orderCart = orderToCancel.getOrderCart();
        User user = orderToCancel.getUser();

        Long usedCouponAmount = orderCart.getUsedCouponAmount();
        if (usedCouponAmount > 0) {
            couponService.refundCoupon(user, usedCouponAmount);
        }
    }

    private void refundOrderAmount(OrderCart orderCart, Long orderTotalPrice) {
        orderCartService.refundOrder(orderCart, orderTotalPrice);
    }

    private void refundStampAmount(User user, Long orderTotalPrice) {
        userService.refundStamp(user, orderTotalPrice);
    }

    private OrderResponseDto.Cancel cancelOrderProcess (final @NotNull Order orderToCancel,
                                                        final @NotNull OrderRequestDto.Cancel request) {
        orderService.cancelOrder(orderToCancel);

        OrderCancel cancellationMessage = orderCancelService.saveCancelMessage(orderToCancel, request);

        refundUsedCouponAmount(orderToCancel);
        refundOrderAmount(orderToCancel.getOrderCart(), orderToCancel.getTotalPrice());
        refundStampAmount(orderToCancel.getUser(), orderToCancel.getTotalPrice());

        return OrderResponseDto.Cancel.toDto(orderToCancel, cancellationMessage);
    }

    public OrderResponseDto.Complete completeOrder(final @NotNull Long orderId) {

        Order order = orderService.findByOrderIdAndOrderedFetchUserAndOrderCart(orderId);

        User user = order.getUser(); // 호출 알림
        return OrderResponseDto.Complete.toDto(orderService.completeOrder(order));
    }
}














