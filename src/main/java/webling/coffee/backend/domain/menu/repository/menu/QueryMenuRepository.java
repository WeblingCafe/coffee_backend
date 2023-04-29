package webling.coffee.backend.domain.menu.repository.menu;

import jakarta.validation.constraints.NotNull;
import webling.coffee.backend.domain.menu.dto.response.MenuResponseDto;
import webling.coffee.backend.domain.menu.entity.Menu;
import webling.coffee.backend.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface QueryMenuRepository {

    List<MenuResponseDto.Find> findAllAvailable ();

    Optional<Menu> findByIdAndAvailable(final @NotNull Long id);

    List<MenuResponseDto.Find> getFavoriteMenuList(final @NotNull User user);

}
