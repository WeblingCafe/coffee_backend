package webling.coffee.backend.domain.menuCategory.repository;

import webling.coffee.backend.domain.menuCategory.entity.MenuCategory;

import java.util.List;

public interface QueryMenuCategoryRepository {
    List<MenuCategory> findAllByCategoryId(Long categoryId);

    List<MenuCategory> findAllByCategory();

}
