package webling.coffee.backend.domain.menu.repository;

import jakarta.validation.constraints.NotNull;
import webling.coffee.backend.domain.menu.dto.response.MenuResponseDto;
import webling.coffee.backend.domain.menu.entity.Menu;

import java.util.List;
import java.util.Optional;

public interface QueryMenuRepository {

    List<MenuResponseDto.Find> findAllAvailable ();

    Optional<Menu> findByIdAndAvailable(final @NotNull Long id);
}
