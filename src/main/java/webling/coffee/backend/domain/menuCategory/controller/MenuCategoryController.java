package webling.coffee.backend.domain.menuCategory.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webling.coffee.backend.domain.menuCategory.dto.request.MenuCategoryRequestDto;
import webling.coffee.backend.domain.menuCategory.dto.response.MenuCategoryResponseDto;
import webling.coffee.backend.domain.menuCategory.service.MenuCategoryFacade;
import webling.coffee.backend.global.annotation.AuthRequired;

import static webling.coffee.backend.global.enums.UserRole.DEVELOPER;
import static webling.coffee.backend.global.enums.UserRole.MANAGER;

@Slf4j
@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class MenuCategoryController {

    private final MenuCategoryFacade menuCategoryFacade;

    @AuthRequired (roles = {MANAGER, DEVELOPER})
    @PostMapping("")
    public ResponseEntity<MenuCategoryResponseDto.Create> createCategory (final @NotNull @RequestBody MenuCategoryRequestDto.Create request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuCategoryFacade.create(request));
    }

    @AuthRequired (roles = {MANAGER, DEVELOPER})
    @PatchMapping("/{id}")
    public ResponseEntity<MenuCategoryResponseDto.Update> updateCategory (final @NotNull @PathVariable(value = "id") Long categoryId,
                                                                          final @NotNull @RequestBody MenuCategoryRequestDto.Update request) {
        return ResponseEntity.ok()
                .body(menuCategoryFacade.update(categoryId, request));
    }
}
