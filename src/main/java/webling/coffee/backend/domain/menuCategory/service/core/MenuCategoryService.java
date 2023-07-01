package webling.coffee.backend.domain.menuCategory.service.core;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.menuCategory.dto.request.MenuCategoryRequestDto;
import webling.coffee.backend.domain.menuCategory.entity.MenuCategory;
import webling.coffee.backend.domain.menuCategory.repository.MenuCategoryRepository;
import webling.coffee.backend.global.responses.errors.codes.MenuCategoryErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;

import java.util.List;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class MenuCategoryService {

    private final MenuCategoryRepository menuCategoryRepository;

    @Transactional (readOnly = true)
    public MenuCategory findById (final @NotNull Long categoryId) {
        return menuCategoryRepository.findByMenuCategoryId(categoryId)
                .orElseThrow(() -> new RestBusinessException(MenuCategoryErrorCode.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public boolean existsByCategoryName (final @NotBlank String categoryName) {
        return menuCategoryRepository.existsByCategoryName(categoryName);
    }

    public MenuCategory create(final @NotNull MenuCategoryRequestDto.Create request) {
        return menuCategoryRepository.save(MenuCategory.create(request));
    }

    public MenuCategory update(final @NotNull MenuCategory menuCategory, final @NotNull MenuCategoryRequestDto.Update request) {
        return menuCategoryRepository.save(MenuCategory.update(menuCategory, request));
    }

    public MenuCategory delete(final MenuCategory menuCategory) {
        return menuCategoryRepository.save(MenuCategory.delete(menuCategory));
    }

    public List<MenuCategory> findAll() {
        return menuCategoryRepository.findAll();
    }

    public List<MenuCategory> findAllByCategoryId(Long categoryId) {
        return menuCategoryRepository.findAllByCategoryId(categoryId);
    }

    public List<MenuCategory> findAllByCategory() {
        return menuCategoryRepository.findAllByCategory();
    }

}
