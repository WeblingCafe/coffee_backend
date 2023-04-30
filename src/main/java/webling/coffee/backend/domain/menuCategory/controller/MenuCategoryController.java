package webling.coffee.backend.domain.menuCategory.controller;

import io.swagger.v3.oas.annotations.Operation;
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

import static webling.coffee.backend.global.enums.UserRole.*;

@Slf4j
@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class MenuCategoryController {

    private final MenuCategoryFacade menuCategoryFacade;

    @Operation(
            summary = "메뉴 카테고리 생성",
            description = """
                    ## [메뉴 카테고리 생성 API]
                    ### 메뉴 카테고리를 생성합니다.
                    
                    ## [호출 권한]
                    ### BARISTA, DEVELOPER
                    
                    ## [Exceptions]
                    ### MenuCategoryErrorCode.DUPLICATION : 메뉴카테고리 이름이 중복되는 경우 해당 예외를 리턴합니다.
                    """
    )
    @AuthRequired (roles = {BARISTA, DEVELOPER})
    @PostMapping("")
    public ResponseEntity<MenuCategoryResponseDto.Create> createCategory (final @NotNull @RequestBody MenuCategoryRequestDto.Create request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuCategoryFacade.create(request));
    }

    @Operation(
            summary = "메뉴 카테고리 변경",
            description = """
                    ## [메뉴 카테고리 변경 API]
                    ### 기존의 메뉴 카테고리를 변경합니다.
                    ### 메뉴 카테고리의 식별자인 id 를 통해 변경할 카테고리를 가지고 옵니다.
                    
                    ## [호출 권한]
                    ### BARISTA, DEVELOPER
                    
                    ## [Exceptions]
                    ### MenuCategoryErrorCode.NOT_FOUND : 변경하려는 메뉴카테고리를 찾을 수 없는 경우 해당 예외를 리턴합니다.
                    ### MenuCategoryErrorCode.DUPLICATION : 변경하려는 메뉴카테고리 이름이 이미 존재하는 경우 해당 예외를 리턴합니다.
                    """
    )
    @AuthRequired (roles = {BARISTA, DEVELOPER})
    @PatchMapping("/{categoryId}")
    public ResponseEntity<MenuCategoryResponseDto.Update> updateCategory (final @NotNull @PathVariable Long categoryId,
                                                                          final @NotNull @RequestBody MenuCategoryRequestDto.Update request) {
        return ResponseEntity.ok()
                .body(menuCategoryFacade.update(categoryId, request));
    }
}
