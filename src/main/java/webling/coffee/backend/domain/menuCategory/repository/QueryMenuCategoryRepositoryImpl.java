package webling.coffee.backend.domain.menuCategory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import webling.coffee.backend.domain.menuCategory.entity.MenuCategory;

import java.util.List;
import java.util.Objects;

import static webling.coffee.backend.domain.menu.entity.QMenu.menu;
import static webling.coffee.backend.domain.menuCategory.entity.QMenuCategory.menuCategory;

@RequiredArgsConstructor
public class QueryMenuCategoryRepositoryImpl implements QueryMenuCategoryRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MenuCategory> findAllByCategoryId(Long categoryId) {
        return jpaQueryFactory.selectFrom(menuCategory)
                .join(menuCategory.menuList, menu)
                .fetchJoin()
                .where(
                        Objects.requireNonNull(menuCategory.menuCategoryId.eq(categoryId))
                )
                .fetch();
    }

    @Override
    public List<MenuCategory> findAllByCategory() {
        return jpaQueryFactory.selectFrom(menuCategory)
                .join(menuCategory.menuList, menu)
                .fetchJoin()
                .fetch();
    }
}
