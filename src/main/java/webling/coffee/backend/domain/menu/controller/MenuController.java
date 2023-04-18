package webling.coffee.backend.domain.menu.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webling.coffee.backend.domain.menu.dto.request.MenuRequestDto;
import webling.coffee.backend.domain.menu.dto.response.MenuResponseDto;
import webling.coffee.backend.domain.menu.service.MenuFacade;
import webling.coffee.backend.global.annotation.AuthRequired;
import webling.coffee.backend.global.enums.UserRole;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/menus")
@RestController
public class MenuController {

    private final MenuFacade menuFacade;

    @AuthRequired(roles = {UserRole.MANAGER})
    @Operation(summary = "메뉴 등록")
    @PostMapping("")
    public ResponseEntity<MenuResponseDto.Create> createMenu (final @RequestBody MenuRequestDto.Create request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(menuFacade.createMenu(request));
    }

}
