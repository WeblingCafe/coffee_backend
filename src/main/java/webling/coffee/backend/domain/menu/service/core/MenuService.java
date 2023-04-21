package webling.coffee.backend.domain.menu.service.core;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.menu.dto.request.MenuRequestDto;
import webling.coffee.backend.domain.menu.dto.response.MenuResponseDto;
import webling.coffee.backend.domain.menu.entity.Menu;
import webling.coffee.backend.domain.menu.repository.MenuRepository;
import webling.coffee.backend.domain.menuCategory.entity.MenuCategory;
import webling.coffee.backend.global.responses.errors.codes.MenuErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;

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
                .orElseThrow(() -> new RestBusinessException(MenuErrorCode.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<MenuResponseDto.Find> findAllAvailable() {
        return menuRepository.findAllAvailable();
    }
}
