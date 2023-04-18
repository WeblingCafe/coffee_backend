package webling.coffee.backend.domain.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webling.coffee.backend.domain.menu.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    boolean existsByMenuName(String menuName);
}
