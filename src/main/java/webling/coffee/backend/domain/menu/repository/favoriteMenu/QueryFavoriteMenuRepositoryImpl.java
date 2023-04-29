package webling.coffee.backend.domain.menu.repository.favoriteMenu;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import webling.coffee.backend.domain.menu.entity.FavoriteMenu;
import webling.coffee.backend.domain.menu.entity.Menu;
import webling.coffee.backend.domain.user.entity.User;

import static webling.coffee.backend.domain.menu.entity.QFavoriteMenu.favoriteMenu;

@RequiredArgsConstructor
public class QueryFavoriteMenuRepositoryImpl implements QueryFavoriteMenuRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public FavoriteMenu findByUserAndMenu(final User user, final Menu menu) {
        return jpaQueryFactory.selectFrom(favoriteMenu)
                        .where(
                                favoriteMenu.user.eq(user),
                                favoriteMenu.menu.eq(menu)
                        )
                .fetchOne();
    }
}
