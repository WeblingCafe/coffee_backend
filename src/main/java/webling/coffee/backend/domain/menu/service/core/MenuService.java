package webling.coffee.backend.domain.menu.service.core;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.menu.dto.request.MenuRequestDto;
import webling.coffee.backend.domain.menu.dto.response.MenuResponseDto;
import webling.coffee.backend.domain.menu.entity.FavoriteMenu;
import webling.coffee.backend.domain.menu.entity.Menu;
import webling.coffee.backend.domain.menu.repository.favoriteMenu.FavoriteMenuRepository;
import webling.coffee.backend.domain.menu.repository.menu.MenuRepository;
import webling.coffee.backend.domain.menuCategory.entity.MenuCategory;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.global.responses.errors.codes.MenuErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;

    private final FavoriteMenuRepository favoriteMenuRepository;

    public boolean isDuplicationByMenuName(final @NotBlank String menuName) {
        return menuRepository.existsByMenuName(menuName);
    }

    public Menu create(final @NotNull MenuCategory category,
                       final @NotNull MenuRequestDto.Create request) {
        return menuRepository.save(Menu.create(category, request));
    }

    @Transactional(readOnly = true)
    public Menu findById(final @NotNull Long id) {

        return menuRepository.findById(id)
                .orElseThrow(() -> new RestBusinessException.NotFound(MenuErrorCode.NOT_FOUND));
    }

    @Transactional (readOnly = true)
    public Menu findByIdAndAvailable (final @NotNull Long id) {
        return menuRepository.findByIdAndAvailable(id)
                .orElseThrow(() -> new RestBusinessException.NotFound(MenuErrorCode.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<MenuResponseDto.Find> findAllAvailable() {
        return menuRepository.findAllAvailable();
    }

    public Menu updateMenuWithCategory(Menu menu, MenuCategory menuCategory, MenuRequestDto.Update request) {
        return menuRepository.save(Menu.updateWithCategory(menu, menuCategory, request));
    }

    public Menu updateMenu(Menu menu, MenuRequestDto.Update request) {
        return menuRepository.save(Menu.update(menu, request));
    }

    public Menu soldOut(Menu menu) {
        return Menu.soldOut(menu);
    }

    public Menu restore(Menu menu) {
        return Menu.restore(menu);
    }

    public void saveFavoriteMenu(final User user, final Menu menu) {

        FavoriteMenu favoriteMenu = favoriteMenuRepository.findByUserAndMenu(user, menu);

        if (favoriteMenu != null) {
            FavoriteMenu.updateFavorite(favoriteMenu);
        } else {
            favoriteMenuRepository.save(FavoriteMenu.toEntity(user, menu));
        }
    }

    public List<MenuResponseDto.Find> getFavoriteMenuList(final @NotNull User user) {
        return menuRepository.getFavoriteMenuList(user);
    }
}
