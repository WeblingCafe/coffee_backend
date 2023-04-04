package webling.coffee.backend.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import webling.coffee.backend.domain.user.entity.User;

import static webling.coffee.backend.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class QueryUserRepositoryImpl implements QueryUserRepository{

    private final JPAQueryFactory jpaQueryFactory ;

    @Override
    public User findByUsername(String username) {
        return jpaQueryFactory.selectFrom(user)
                .where(user.username.eq(username))
                .fetchOne()
                ;
    }
}
