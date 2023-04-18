package webling.coffee.backend.domain.menu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.menu.dto.request.MenuRequestDto;
import webling.coffee.backend.domain.menu.dto.response.MenuResponseDto;
import webling.coffee.backend.domain.menu.service.core.MenuService;
import webling.coffee.backend.domain.menuCategory.entity.MenuCategory;
import webling.coffee.backend.domain.menuCategory.service.core.MenuCategoryService;
import webling.coffee.backend.global.errors.codes.MenuErrorCode;
import webling.coffee.backend.global.errors.exceptions.RestBusinessException;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MenuFacade {

    private final MenuService menuService;

    private final MenuCategoryService menuCategoryService;

    public MenuResponseDto.Create createMenu(final MenuRequestDto.Create request) {

        if (menuService.isDuplicate(request.getMenuName())) {
            throw new RestBusinessException(MenuErrorCode.DUPLICATION);
        }

        MenuCategory menuCategory = menuCategoryService.findById(request.getMenuCategoryId());

        return MenuResponseDto.Create.toDto(menuService.create(menuCategory, request));
    }

}
