package webling.coffee.backend.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import webling.coffee.backend.domain.user.dto.response.QUserResponseDto_Find;
import webling.coffee.backend.domain.user.dto.response.UserResponseDto;
import webling.coffee.backend.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

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
    public List<User> settlementAll() {
        return jpaQueryFactory.selectFrom(user)
                .leftJoin(user.orderCart, orderCart)
                .fetchJoin()
                .leftJoin(orderCart.orderList, order)
                .fetchJoin()
                .orderBy(
                        user.userId.asc(),
                        orderCart.regDate.asc(),
                        order.regDate.asc()
                )
                .fetch()
                ;
    }

}
