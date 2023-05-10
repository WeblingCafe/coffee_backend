package webling.coffee.backend.domain.user.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import webling.coffee.backend.domain.order.dto.request.SettlementRequestDto;
import webling.coffee.backend.domain.user.dto.response.QUserResponseDto_Find;
import webling.coffee.backend.domain.user.dto.response.UserResponseDto;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.global.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static webling.coffee.backend.domain.coupon.entity.QCoupon.coupon;
import static webling.coffee.backend.domain.order.entity.QOrder.order;
import static webling.coffee.backend.domain.order.entity.QOrderCart.orderCart;
import static webling.coffee.backend.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class QueryUserRepositoryImpl implements QueryUserRepository{

    private final JPAQueryFactory jpaQueryFactory ;

    @Override
    public boolean checkUserByEmail(final @NotBlank String email) {
        return jpaQueryFactory.selectFrom(user)
                .where(
                        user.email.eq(email)
                )
                .fetchOne() != null
                ;
    }

    @Override
    public List<UserResponseDto.Find> findAllByIsAvailableTrue() {
        return jpaQueryFactory.select(new QUserResponseDto_Find(
                user.userId,
                user.email,
                user.username,
                user.nickname,
                user.phoneNumber,
                user.birthDate,
                user.userRole,
                user.stamps,
                user.teamName
        ))
                .from(user)
                .where(
                        user.isAvailable.isTrue()
                )
                .fetch();
    }

    @Override
    public Optional<User> findByIdAndIsAvailableTrue(final Long id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(user)
                .where(
                        user.userId.eq(id),
                        user.isAvailable.isTrue()
                )
                .fetchOne())
                ;
    }

    @Override
    public Optional<User> findByIdFetchCoupon(Long id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(user)
                .join(user.coupons, coupon)
                .fetchJoin()
                .where(
                        user.userId.eq(id),
                        coupon.isAvailable.isTrue()
                )
                .fetchOne()
        );
    }

    @Override
    public List<User> settlementAllBySearchOptions(final @NotNull SettlementRequestDto.Search request) {
        return jpaQueryFactory.selectFrom(user)
                .leftJoin(user.orderCart, orderCart)
                .fetchJoin()
                .leftJoin(orderCart.orderList, order)
                .fetchJoin()
                .where(
                    order.orderStatus.eq(OrderStatus.COMPLETED),
                    regDateBetween(request.getRegDate()),
                    usernameLike(request.getUsername()),
                    nicknameLike(request.getUserNickname())
                )
                .fetch()
                ;
    }

    @Override
    public User settlementMeBySearchOptions(final User entity, final SettlementRequestDto.RegDate request) {
        return jpaQueryFactory.selectFrom(user)
                .leftJoin(user.orderCart, orderCart)
                .fetchJoin()
                .leftJoin(orderCart.orderList, order)
                .fetchJoin()
                .where(
                        user.eq(entity),
                        order.orderStatus.eq(OrderStatus.COMPLETED),
                        regDateBetween(request)
                )
                .fetchFirst()
                ;
    }

    private BooleanExpression regDateBetween (final @NotNull SettlementRequestDto.RegDate request) {

        if (request.getFromDate() == null && request.getToDate() == null) {
            return null;
        }

        if (request.getFromDate() == null) {
            return orderCart.regDate.between(LocalDateTime.now().minusDays(15), request.getToDate());
        }

        if (request.getToDate() == null) {
            return orderCart.regDate.between(request.getFromDate(), LocalDateTime.now());
        }

        return orderCart.regDate.between(request.getFromDate(), request.getToDate());
    }

    private BooleanExpression usernameLike (final @NotBlank String username) {
        return StringUtils.hasText(username) ? user.username.contains(username) : null;
    }

    private BooleanExpression nicknameLike (final @NotBlank String nickname) {
        return StringUtils.hasText(nickname) ? user.nickname.contains(nickname) : null;
    }

}
