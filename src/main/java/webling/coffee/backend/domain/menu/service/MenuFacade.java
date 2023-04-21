package webling.coffee.backend.domain.menu.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.menu.dto.request.MenuRequestDto;
import webling.coffee.backend.domain.menu.dto.response.MenuResponseDto;
import webling.coffee.backend.domain.menu.entity.Menu;
import webling.coffee.backend.domain.menu.service.core.MenuService;
import webling.coffee.backend.domain.menuCategory.entity.MenuCategory;
import webling.coffee.backend.domain.menuCategory.service.core.MenuCategoryService;
import webling.coffee.backend.global.responses.errors.codes.MenuErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;

import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MenuFacade {

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

        return MenuResponseDto.Update.toDto(Menu.update(menu, request));
    }

    public MenuResponseDto.SoldOut soldOut(Long id) {

        Menu menu = menuService.findById(id);

        return MenuResponseDto.SoldOut.toDto(Menu.soldOut (menu));
    }

    public MenuResponseDto.Find findByIdAndAvailable(Long id) {
        Menu menu = menuService.findById(id);

        if (!menu.isAvailable()) {
            throw new RestBusinessException(MenuErrorCode.NOT_AVAILABLE);
        }

        return MenuResponseDto.Find.toDto (menu);
    }

    public List<MenuResponseDto.Find> findAllAndAvailable() {
        return menuService.findAllAvailable();
    }
}
