package webling.coffee.backend.domain.menu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.menu.dto.request.MenuRequestDto;
import webling.coffee.backend.domain.menu.dto.response.MenuResponseDto;
import webling.coffee.backend.domain.menu.service.core.MenuService;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MenuFacade {

    private final MenuService menuService;

    public MenuResponseDto.Create createMenu(final Long userId, final MenuRequestDto.Create request) {
        return null;
    }

}
