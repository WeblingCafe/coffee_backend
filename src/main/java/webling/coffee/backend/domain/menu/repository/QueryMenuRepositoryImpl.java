package webling.coffee.backend.domain.menu.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import webling.coffee.backend.domain.menu.dto.response.MenuResponseDto;
import webling.coffee.backend.domain.menu.dto.response.QMenuResponseDto_Find;

import java.util.List;

import static webling.coffee.backend.domain.menu.entity.QMenu.menu;
import static webling.coffee.backend.domain.menuCategory.entity.QMenuCategory.menuCategory;

@RequiredArgsConstructor
public class QueryMenuRepositoryImpl implements QueryMenuRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MenuResponseDto.Find> findAllAvailable() {
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
                .fetch();
    }
}
