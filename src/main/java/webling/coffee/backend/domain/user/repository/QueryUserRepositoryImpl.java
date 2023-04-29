package webling.coffee.backend.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import webling.coffee.backend.domain.user.dto.response.QUserResponseDto_Find;
import webling.coffee.backend.domain.user.dto.response.UserResponseDto;
import webling.coffee.backend.domain.user.entity.User;

import java.util.List;

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

}
