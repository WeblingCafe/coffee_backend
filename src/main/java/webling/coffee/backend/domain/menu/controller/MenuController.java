package webling.coffee.backend.domain.menu.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import webling.coffee.backend.domain.menu.dto.request.MenuRequestDto;
import webling.coffee.backend.domain.menu.dto.response.MenuResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import webling.coffee.backend.domain.menu.service.MenuFacade;
import webling.coffee.backend.global.annotation.AuthRequired;
import webling.coffee.backend.global.annotation.AuthUser;
import webling.coffee.backend.global.context.UserAuthentication;
import webling.coffee.backend.global.enums.UserRole;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MenuController {

    private final MenuFacade menuFacade;

    @AuthRequired(roles = UserRole.MANAGER)
    @PostMapping("")
    public ResponseEntity<MenuResponseDto.Create> createMenu (final @AuthUser UserAuthentication authentication,
                                                              final MenuRequestDto.@NotNull Create request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(menuFacade.createMenu(authentication.getUserId(), request));
    }

}
