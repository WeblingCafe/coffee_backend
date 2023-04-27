package webling.coffee.backend.domain.order.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.coupon.service.CouponService;
import webling.coffee.backend.domain.menu.service.core.MenuService;
import webling.coffee.backend.domain.order.dto.request.OrderRequestDto;
import webling.coffee.backend.domain.order.dto.response.OrderResponseDto;
import webling.coffee.backend.domain.order.entity.Order;
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
    private final MenuService menuService;
    private final CouponService couponService;

    public List<OrderResponseDto.Create> create(final @NotNull Long userId,
                                          final @NotNull List<OrderRequestDto.Create> request) {

        User user = userService.findById(userId);
        List<Order> orderEntityList = new ArrayList<>();

        for (OrderRequestDto.Create orderRequest : request) {
            couponService.useCoupons(couponService.findAllByUserAndIsAvailable(user), orderRequest.getCouponAmount());

            Order orderEntity = orderService.create(
                    user,
                    userService.findById(orderRequest.getRecipientId()),
                    menuService.findByIdAndAvailable(orderRequest.getMenuId()),
                    orderRequest);

            orderEntityList.add(orderEntity);
        }

        orderService.addCart(orderCartService.save(orderEntityList, user), orderEntityList);

        userService.addStamps(user, request);
        couponService.issueCouponByStamp(user);

        return orderEntityList.stream()
                .map(OrderResponseDto.Create::toDto)
                .collect(Collectors.toList());
    }
}














