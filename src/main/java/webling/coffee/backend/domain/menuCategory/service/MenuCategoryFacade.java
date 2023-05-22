package webling.coffee.backend.domain.menuCategory.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.menuCategory.dto.request.MenuCategoryRequestDto;
import webling.coffee.backend.domain.menuCategory.dto.response.MenuCategoryResponseDto;
import webling.coffee.backend.domain.menuCategory.entity.MenuCategory;
import webling.coffee.backend.domain.menuCategory.service.core.MenuCategoryService;
import webling.coffee.backend.global.responses.errors.codes.MenuCategoryErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MenuCategoryFacade {

    private final MenuCategoryService menuCategoryService;

    public MenuCategoryResponseDto.Create create(final @NotNull MenuCategoryRequestDto.Create request) {

        if (menuCategoryService.existsByCategoryName(request.getCategoryName())) {
            throw new RestBusinessException.Duplication(MenuCategoryErrorCode.DUPLICATION);
        }

        return MenuCategoryResponseDto.Create.toDto(menuCategoryService.create(request));
    }

    public MenuCategoryResponseDto.Update update(final @NotNull Long categoryId, final @NotNull MenuCategoryRequestDto.Update request) {

        if (menuCategoryService.existsByCategoryName(request.getCategoryName())) {
            throw new RestBusinessException.Duplication(MenuCategoryErrorCode.DUPLICATION);
        }

        MenuCategory menuCategory = menuCategoryService.findById(categoryId);

        return MenuCategoryResponseDto.Update.toDto(menuCategoryService.update(menuCategory, request));
    }

    public List<MenuCategoryResponseDto.Find> findAll() {
        return menuCategoryService.findAll().stream()
                .map(MenuCategoryResponseDto.Find::toDto)
                .collect(Collectors.toList());
    }
}
