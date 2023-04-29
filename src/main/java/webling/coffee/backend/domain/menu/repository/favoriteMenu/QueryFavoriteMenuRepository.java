package webling.coffee.backend.domain.menu.repository.favoriteMenu;

import webling.coffee.backend.domain.menu.entity.FavoriteMenu;
import webling.coffee.backend.domain.menu.entity.Menu;
import webling.coffee.backend.domain.user.entity.User;

public interface QueryFavoriteMenuRepository {

    FavoriteMenu findByUserAndMenu(User user, Menu menu);
}
