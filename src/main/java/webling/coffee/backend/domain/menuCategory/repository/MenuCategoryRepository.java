package webling.coffee.backend.domain.menuCategory.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import webling.coffee.backend.domain.menuCategory.entity.MenuCategory;

import java.util.Optional;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {

    Optional<MenuCategory> findByMenuCategoryId(final @NotNull Long categoryId);
}
