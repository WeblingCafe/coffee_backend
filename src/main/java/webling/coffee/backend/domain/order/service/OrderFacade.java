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
import webling.coffee.backend.domain.order.service.core.OrderService;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.domain.user.service.core.UserService;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final UserService userService;
    private final OrderService orderService;
    private final MenuService menuService;
    private final CouponService couponService;

    public OrderResponseDto.Create create(final @NotNull Long userId,
                                          final @NotNull OrderRequestDto.Create request) {

        User user = userService.findById(userId);

        couponService.useCoupons (couponService.findAllByUserAndIsAvailable(user), request.getCouponAmount());

        return OrderRequestDto.Create.toDto(
                orderService.create(
                        user,
                        userService.findById(request.getRecipientId()),
                        menuService.findById(request.getMenuId()),
                        request)
        );
    }



}














