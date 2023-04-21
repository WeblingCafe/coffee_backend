package webling.coffee.backend.domain.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webling.coffee.backend.domain.menu.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long>, QueryMenuRepository {
    boolean existsByMenuName(String menuName);
}
