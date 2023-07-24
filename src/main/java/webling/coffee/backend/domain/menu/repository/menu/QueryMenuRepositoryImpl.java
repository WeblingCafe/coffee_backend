package webling.coffee.backend.domain.menu.repository.menu;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import webling.coffee.backend.domain.menu.dto.request.MenuRequestDto;
import webling.coffee.backend.domain.menu.dto.response.MenuResponseDto;
import webling.coffee.backend.domain.menu.dto.response.QMenuResponseDto_Find;
import webling.coffee.backend.domain.menu.entity.Menu;
import webling.coffee.backend.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

import static webling.coffee.backend.domain.menu.entity.QFavoriteMenu.favoriteMenu;
import static webling.coffee.backend.domain.menu.entity.QMenu.menu;
import static webling.coffee.backend.domain.menuCategory.entity.QMenuCategory.menuCategory;

@RequiredArgsConstructor
public class QueryMenuRepositoryImpl implements QueryMenuRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MenuResponseDto.Find> findAllAvailable(final @NotNull MenuRequestDto.Search request) {
        return jpaQueryFactory.select(new QMenuResponseDto_Find(
                menu.menuId,
                menu.menuName,
                menuCategory.categoryName,
                menu.price,
                menu.menuPhotoUrl,
                menu.hotAvailable,
                menu.coldAvailable
        ))
                .from(menu)
                .join(menu.category, menuCategory)
                .where(
                        menu.isAvailable.isTrue()
                )
                .limit(request.getSize())
                .offset(request.getOffSet())
                .fetch();
    }

    @Override
    public Optional<Menu> findByIdAndAvailable(Long id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(menu)
                .where(
                        menu.menuId.eq(id),
                        menu.isAvailable.isTrue()
                )
                .fetchOne())
                ;
    }

    @Override
    public List<MenuResponseDto.Find> getFavoriteMenuList(final User user) {
        return jpaQueryFactory.select(new QMenuResponseDto_Find(
                menu.menuId,
                menu.menuName,
                menuCategory.categoryName,
                menu.price,
                menu.menuPhotoUrl,
                menu.hotAvailable,
                menu.coldAvailable
        ))
                .from(menu)
                .join(menu.category, menuCategory)
                .join(favoriteMenu)
                .where(
                        favoriteMenu.user.eq(user),
                        favoriteMenu.menu.eq(menu),
                        favoriteMenu.favorite.isTrue()
                )
                .fetch()
                ;
    }

}
