package webling.coffee.backend.domain.menuCategory.repository;

import jakarta.validation.constraints.NotNull;
import webling.coffee.backend.domain.menu.dto.request.MenuRequestDto;
import webling.coffee.backend.domain.menuCategory.entity.MenuCategory;

import java.util.List;

public interface QueryMenuCategoryRepository {
    List<MenuCategory> findAllByCategoryId(Long categoryId);

    List<MenuCategory> findAllByCategory(final @NotNull MenuRequestDto.Search request);

}
