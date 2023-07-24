package webling.coffee.backend.domain.menu.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.menu.dto.request.MenuRequestDto;
import webling.coffee.backend.domain.menu.dto.response.MenuResponseDto;
import webling.coffee.backend.domain.menu.entity.FavoriteMenu;
import webling.coffee.backend.domain.menu.entity.Menu;
import webling.coffee.backend.domain.menu.service.core.MenuService;
import webling.coffee.backend.domain.menuCategory.entity.MenuCategory;
import webling.coffee.backend.domain.menuCategory.service.core.MenuCategoryService;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.domain.user.service.core.UserService;
import webling.coffee.backend.global.responses.errors.codes.MenuErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MenuFacade {

    private final UserService userService;
    private final MenuService menuService;
    private final MenuCategoryService menuCategoryService;

    public MenuResponseDto.Create createMenu(final @NotNull MenuRequestDto.Create request) {

        if (menuService.isDuplicationByMenuName(request.getMenuName())) {
            throw new RestBusinessException(MenuErrorCode.DUPLICATION);
        }

        MenuCategory menuCategory = menuCategoryService.findById(request.getMenuCategoryId());

        return MenuResponseDto.Create.toDto(menuService.create(menuCategory, request));
    }

    public MenuResponseDto.Update updateMenu(final @NotNull Long id, final @NotNull MenuRequestDto.Update request) {

        Menu menu = menuService.findById(id);

        if (menuService.isDuplicationByMenuName(request.getMenuName()) && !menu.getMenuName().equals(request.getMenuName())) {
            throw new RestBusinessException(MenuErrorCode.DUPLICATION);
        }

        if (request.getMenuCategoryId() != null) {
            MenuCategory menuCategory = menuCategoryService.findById(request.getMenuCategoryId());
            return MenuResponseDto.Update.toDto(menuService.updateMenuWithCategory(menu, menuCategory, request));
        }

        return MenuResponseDto.Update.toDto(menuService.updateMenu(menu, request));

    }

    @Transactional (readOnly = true)
    public MenuResponseDto.Find findByIdAndAvailable(Long id) {
        Menu menu = menuService.findById(id);

        if (!menu.isAvailable()) {
            throw new RestBusinessException(MenuErrorCode.NOT_AVAILABLE);
        }

        return MenuResponseDto.Find.toDto (menu);
    }

    @Transactional (readOnly = true)
    public List<MenuResponseDto.Find> findAllAndAvailable(final @NotNull MenuRequestDto.Search request) {
        return menuService.findAllAvailable(request);
    }

    public MenuResponseDto.SoldOut soldOut(Long id) {

        Menu menu = menuService.findById(id);

        if (!menu.isAvailable()) {
            throw new RestBusinessException(MenuErrorCode.NOT_AVAILABLE);
        }

        return MenuResponseDto.SoldOut.toDto(menuService.soldOut(menu));
    }

    public MenuResponseDto.Restore restore(Long id) {

        Menu menu = menuService.findById(id);

        if (menu.isAvailable()) {
            throw new RestBusinessException(MenuErrorCode.ALREADY_AVAILABLE);
        }

        return MenuResponseDto.Restore.toDto(menuService.restore(menu));
    }

    public FavoriteMenu saveFavoriteMenu(final @NotNull Long userId, final @NotNull Long menuId) {
        User user = userService.findByIdAndIsAvailableTrue(userId);

        Menu menu = menuService.findByIdAndAvailable(menuId);

        return menuService.saveFavoriteMenu(user, menu);
    }

    public List<MenuResponseDto.Find> getFavoriteMenuList(final @NotNull Long userId) {

        User user = userService.findByIdAndIsAvailableTrue(userId);

        return menuService.getFavoriteMenuList(user);
    }

    public List<MenuResponseDto.FindByCategory> findAllByCategory() {
        return menuCategoryService.findAllByCategory().stream().map(MenuResponseDto.FindByCategory::toDto).collect(Collectors.toList());
    }

    public List<MenuResponseDto.FindByCategory> findAllByCategoryId(final Long categoryId) {
        return menuCategoryService.findAllByCategoryId(categoryId).stream().map(MenuResponseDto.FindByCategory::toDto).collect(Collectors.toList());
    }
}
