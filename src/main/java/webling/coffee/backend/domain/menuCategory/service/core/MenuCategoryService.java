package webling.coffee.backend.domain.menuCategory.service.core;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.menuCategory.entity.MenuCategory;
import webling.coffee.backend.domain.menuCategory.repository.MenuCategoryRepository;
import webling.coffee.backend.global.responses.errors.codes.MenuCategoryErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;

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
}
