package webling.coffee.backend.domain.coupon.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.coupon.dto.request.CouponRequestDto;
import webling.coffee.backend.domain.coupon.dto.response.CouponResponseDto;
import webling.coffee.backend.domain.coupon.service.core.CouponService;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.domain.user.service.core.UserService;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CouponFacade {

    private final CouponService couponService;

    private final UserService userService;

    public CouponResponseDto.Create create(final @NotNull String regEmail,
                                           final @NotNull Long userId,
                                           final @NotNull CouponRequestDto.Create request) {

        User user = userService.findByIdAndIsAvailableTrue(userId);

        return CouponResponseDto.Create.toDto(couponService.create(regEmail, user, request));
    }

    public List<CouponResponseDto.Find> findAllByMeOnStatus(final @NotNull Long userId,
                                                            final @NotNull String status) {

        User user = userService.findByIdAndIsAvailableTrue(userId);

        return couponService.findAllByMeOnStatus(user, status);
    }
}
