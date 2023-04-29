package webling.coffee.backend.domain.menu.repository.favoriteMenu;

import org.springframework.data.jpa.repository.JpaRepository;
import webling.coffee.backend.domain.menu.entity.FavoriteMenu;

public interface FavoriteMenuRepository extends JpaRepository<FavoriteMenu, Long>, QueryFavoriteMenuRepository {

}
