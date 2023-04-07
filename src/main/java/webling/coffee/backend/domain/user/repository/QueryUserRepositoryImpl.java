package webling.coffee.backend.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import webling.coffee.backend.domain.user.entity.User;

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

}
