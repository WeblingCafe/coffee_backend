package webling.coffee.backend.domain.menu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webling.coffee.backend.domain.menu.dto.request.MenuRequestDto;
import webling.coffee.backend.domain.menu.dto.response.MenuResponseDto;
import webling.coffee.backend.domain.menu.service.MenuFacade;
import webling.coffee.backend.global.annotation.AuthRequired;
import webling.coffee.backend.global.annotation.AuthUser;
import webling.coffee.backend.global.context.UserAuthentication;

import java.util.List;

import static webling.coffee.backend.global.enums.UserRole.BARISTA;
import static webling.coffee.backend.global.enums.UserRole.DEVELOPER;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/menus")
@RestController
public class MenuController {

    private final MenuFacade menuFacade;

    @Operation(
            summary = "메뉴 등록",
            description =
                    """
                    ## [메뉴 등록 API]
                    ### 메뉴를 등록 합니다.
                    ### 사진을 제외한 모든 요청 객체 값은 필수 입니다.
                    
                    ## [호출 권한]
                    ### BARISTA, DEVELOPER
                    
                    ## [Exceptions]
                    ### 1. MenuErrorCode.DUPLICATION : 메뉴 명이 중복 되었을 경우 해당 예외를 리턴합니다.
                    ### 2. MenuCategoryErrorCode.NOT_FOUND : 등록 할 메뉴의 카테고리를 찾을 수 없을 경우 해당 예외를 리턴합니다.
                    ### 3. MenuErrorCode.PHOTO_UPLOAD_FAILED : 메뉴 등록 시, S3 서버의 문제로 사진이 업로드 되지 않을 경우 해당 예외를 리턴합니다.
                    """
    )
    @AuthRequired(roles = {BARISTA, DEVELOPER})
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MenuResponseDto.Create> createMenu (final @ModelAttribute MenuRequestDto.Create request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(menuFacade.createMenu(request));
    }

    @Operation (
            summary = "메뉴 단일 조회 (판매 가능한 메뉴만 조회)",
            description =
                    """
                    ## [메뉴 단일 조회 API]
                    ### 판매 가능한 메뉴 단건을 조회합니다. (isAvailable 이 true 인 메뉴)
                    ### 메뉴의 식별자인 시퀀스를 통해 조회합니다.
                    
                    ## [호출 권한]
                    ### ALL
                    
                    ## [Exceptions]
                    ### 1. MenuErrorCode.NOT_FOUND : 시퀀스에 해당하는 메뉴를 찾지 못했을 경우 해당 예외를 리턴합니다.
                    ### 2. MenuErrorCode.NOT_AVAILABLE : 시퀀스에 해당하는 메뉴가 현재 품절일 경우 해당 예외를 리턴합니다.
                    """
    )
    @AuthRequired
    @GetMapping ("/{menuId}")
    public ResponseEntity<MenuResponseDto.Find> findById (final @NotNull @PathVariable Long menuId) {

        return ResponseEntity.ok()
                .body(menuFacade.findByIdAndAvailable(menuId));
    }

    @Operation (
            summary = "메뉴 전체 조회 (판매 가능한 메뉴들만 조회)",
            description =
                    """
                    ## [메뉴 전체 조회 API]
                    ### 판매 가능한 메뉴 전체를 조회합니다. (isAvailable 이 true 인 메뉴)
                    
                    ## [호출 권한]
                    ### ALL
                    """
    )
    @AuthRequired
    @GetMapping ("")
    public ResponseEntity<List<MenuResponseDto.Find>> findAll() {

        return ResponseEntity.ok()
                .body(menuFacade.findAllAndAvailable());
    }

    @Operation (
            summary = "메뉴 수정",
            description =
                    """
                    ## [메뉴 수정 API]
                    ### 메뉴를 수정합니다.
                    ### 메뉴의 식별자인 시퀀스를 통해 변경할 메뉴를 조회합니다.
                    ### 변경가능한 내용은 request 필드를 참고해주세요.
                    
                    ## [호출 권한]
                    ### BARISTA, DEVELOPER
                    
                    ## [Exceptions]
                    ### 1. MenuErrorCode.DUPLICATION : 변경할 메뉴의 메뉴명이 이미 존재할 경우 해당 예외를 리턴합니다.
                    ### 2. MenuErrorCode.NOT_FOUND : 시퀀스를 통해 변경할 메뉴를 찾지 못했을 경우 해당 예외를 리턴합니다.
                    ### 3. MenuCategoryErrorCode.NOT_FOUND : 변경할 메뉴 카테고리를 찾지 못했을 경우 해당 예외를 리턴합니다.
                    ### 4. MenuErrorCode.PHOTO_UPLOAD_FAILED : 메뉴 등록 시, S3 서버의 문제로 사진이 업로드 되지 않을 경우 해당 예외를 리턴합니다.
                    """
    )
    @AuthRequired (roles = {BARISTA, DEVELOPER})
    @PatchMapping (value = "/{menuId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MenuResponseDto.Update> updateMenu (final @NotNull @PathVariable Long menuId,
                                                              final @ModelAttribute MenuRequestDto.Update request) {

        return ResponseEntity.ok()
                .body(menuFacade.updateMenu(menuId, request));
    }

    @Operation (
            summary = "품절 처리",
            description =
                    """
                    ## [품절 처리 API]
                    ### 메뉴를 품절 처리 합니다. (isAvailable 을 false 로 변경)
                    ### 메뉴의 식별자인 시퀀스를 통해 품절처리 할 메뉴를 조회합니다.
                    
                    ## [호출 권한]
                    ### BARISTA, DEVELOPER
                    
                    ## [Exceptions]
                    ### MenuErrorCode.NOT_FOUND : 품절 처리할 메뉴를 찾지 못했을 경우 해당 예외를 리턴합니다.
                    ### MenuErrorCode.NOT_AVAILABLE : 해당 메뉴가 이미 품절 처리 되어있을 경우 해당 예외를 리턴합니다.
                    """
    )
    @AuthRequired (roles = {BARISTA, DEVELOPER})
    @PatchMapping ("/soldOut/{menuId}")
    public ResponseEntity<MenuResponseDto.SoldOut> soldOut (final @NotNull @PathVariable Long menuId) {

        return ResponseEntity.ok()
                .body(menuFacade.soldOut(menuId));
    }

    @Operation (
            summary = "판매 가능 처리",
            description =
                    """
                    ## [판매 가능 처리 API]
                    ### 메뉴를 판매 가능 처리 합니다. (isAvailable 을 true 로 변경)
                    ### 메뉴의 식별자인 시퀀스를 통해 품절처리 할 메뉴를 조회합니다.
                    
                    ## [호출 권한]
                    ### BARISTA, DEVELOPER
                    
                    ## [Exceptions]
                    ### 1. MenuErrorCode.NOT_FOUND : 판매 가능 처리 할 메뉴를 찾지 못했을 경우 해당 예외를 리턴합니다.
                    ### 2. MenuErrorCode.ALREADY_AVAILABLE : 해당 메뉴가 이미 판매가능한 경우 해당 예외를 리턴합니다.
                    """
    )
    @AuthRequired (roles = {BARISTA, DEVELOPER})
    @PatchMapping ("/restore/{menuId}")
    public ResponseEntity<MenuResponseDto.Restore> restore (final @NotNull @PathVariable Long menuId) {

        return ResponseEntity.ok()
                .body(menuFacade.restore(menuId));
    }

    @Operation (
            summary = "메뉴 즐겨찾기 등록 / 해제 API ",
            description =
                    """
                    ## [메뉴 즐겨찾기 등록 / 해제 API]
                    ### 메뉴를 즐겨찾기 등록을 하거나 해제 합니다.
                    
                    ## [호출 권한]
                    ### ALL
                    
                    ## [Exceptions]
                    ### 1. UserErrorCode.NOT_FOUND : 현재 로그인 된 유저 정보를 찾지 못할 때 해당 예외를 리턴합니다.
                    ### 2. MenuErrorCode.NOT_FOUND : 해당 메뉴를 찾지 못할 경우 해당 예외를 리턴합니다.
                    """
    )
    @AuthRequired
    @PostMapping ("/me/favorite/{menuId}")
    public ResponseEntity<?> saveFavoriteMenu (final @AuthUser @Parameter(hidden = true) UserAuthentication authentication,
                                               final @NotNull @PathVariable Long menuId) {

        menuFacade.saveFavoriteMenu(authentication.getUserId(), menuId);

        return ResponseEntity.noContent().build();
    }

    @Operation (
            summary = "즐겨찾는 메뉴 리스트 조회",
            description =
                    """
                    ## [즐겨찾는 메뉴 리스트 조회 API]
                    ### 로그인된 유저의 즐겨찾는 메뉴 리스트를 조회합니다.
                    
                    ## [호출 권한]
                    ### ALL
                    
                    ## [Exceptions]
                    ### 1. UserErrorCode.NOT_FOUND : 현재 로그인 된 유저 정보를 찾지 못할 때 해당 예외를 리턴합니다.
                    ### 2. MenuErrorCode.NOT_FOUND : 해당 메뉴를 찾지 못할 경우 해당 예외를 리턴합니다.
                    """
    )
    @AuthRequired
    @GetMapping("/me/favorite")
    public ResponseEntity<List<MenuResponseDto.Find>> getFavoriteMenuList (final @AuthUser @Parameter(hidden = true) UserAuthentication authentication){

        return ResponseEntity.ok()
                .body(menuFacade.getFavoriteMenuList(authentication.getUserId()));
    }

}
