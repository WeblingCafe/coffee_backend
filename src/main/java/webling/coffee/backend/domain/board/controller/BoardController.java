package webling.coffee.backend.domain.board.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webling.coffee.backend.domain.board.dto.request.BoardRequestDto;
import webling.coffee.backend.domain.board.service.BoardFacade;
import webling.coffee.backend.global.annotation.AuthRequired;
import webling.coffee.backend.global.annotation.AuthUser;
import webling.coffee.backend.global.context.UserAuthentication;
import webling.coffee.backend.global.responses.success.codes.BoardSuccessCode;
import webling.coffee.backend.global.responses.success.response.SuccessResponse;

import static webling.coffee.backend.global.enums.UserRole.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/boards")
@RestController
public class BoardController {

    private final BoardFacade boardFacade;

    @Operation(
            summary = "게시판 등록",
            description =
                    """
                    ## [게시판 등록 API]
                    ### 게시판을 등록합니다.
                    
                    ## [호출 권한]
                    ### MANAGER, BARISTA, DEVELOPER
                    
                    ## [Exceptions]
                    ### UserErrorCode.NOT_FOUND : 현재 로그인한 유저 정보를 찾지 못할 경우 해당 예외를 리턴합니다.
                    ### EnumValueErrorCode.BOARD_CATEGORY_VALUE_INVALID : 작성 하려는 게시판의 카테고리 명이 올바르지 않은 경우 해당 예외를 리턴합니다.
                    """,
            externalDocs = @ExternalDocumentation(
                    description = """
                            ## [ENUM]
                            ### 노션 링크를 참고해주세요.
                            """,
                    url = "https://www.notion.so/API-ENUM-c65d84ea50a249dd972d7c8c296750ee")
    )
    @AuthRequired(roles = {MANAGER, BARISTA, DEVELOPER})
    @PostMapping("")
    public ResponseEntity<SuccessResponse> create (final @NotNull @Parameter(hidden = true) @AuthUser UserAuthentication authentication,
                                                   final @NotNull @RequestBody BoardRequestDto.Create request) {

        return SuccessResponse
                .toResponseEntity(
                        BoardSuccessCode.CREATE,
                        boardFacade.create(authentication.getUserId(), request));
    }

    @Operation(
            summary = "게시판 수정",
            description =
                    """
                    ## [게시판 수정 API]
                    ### 본인이 쓴 게시판을 수정합니다.
                    ### 게시판 정보는 로그인한 회원의 정보와 게시판 식별자로 확인합니다.
                    ### 찾아온 게시판의 제목과 내용을 수정합니다.
                    
                    ## [호출 권한]
                    ### MANAGER, BARISTA, DEVELOPER
                    
                    ## [Exceptions]
                    ### UserErrorCode.NOT_FOUND : 로그인한 회원의 정보를 찾을 수 없을 경우 해당 예외를 리턴합니다.
                    ### BoardErrorCode.NOT_FOUND : 게시판 정보를 찾을 수 없을 경우 해당 예외를 리턴합니다.
                    ### BoardErrorCode.IS_NOT_AVAILABLE : 게시판이 비활성화 상태인 경우 해당 예외를 리턴합니다.
                    """
    )
    @AuthRequired (roles = {MANAGER, BARISTA, DEVELOPER})
    @PostMapping("/me/{boardId}")
    public ResponseEntity<SuccessResponse> update (final @NotNull @Parameter(hidden = true) @AuthUser UserAuthentication authentication,
                                                           final @NotNull @PathVariable Long boardId,
                                                           final @NotNull @RequestBody BoardRequestDto.Update request) {

        return SuccessResponse.toResponseEntity(
                BoardSuccessCode.UPDATE,
                boardFacade.update(boardId, authentication.getUserId(), request));
    }

    @Operation(
            summary = "모든 게시판 조회",
            description =
                    """
                    ## [게시판 조회 API]
                    ### 모든 게시판을 조회합니다.
                    ### 게시판의 카테고리에 따라 검색이 가능합니다.
                    ### requestParam 에 아무값도 넣지 않을 경우에는 모든 게시판이 검색됩니다.
                    ### 카테고리명은 ENUM 을 참고해주세요.
                    
                    ## [호출 권한]
                    ### ALL
                    
                    ## [Exceptions]
                    ### EnumValueErrorCode.BOARD_CATEGORY_VALUE_INVALID : 검색 조건에 들어간 카테고리명을 찾을 수 없는 경우 해당 예외를 리턴합니다.
                    """,
            externalDocs = @ExternalDocumentation(
                    description = """
                            ## [ENUM]
                            ### 노션 링크를 참고해주세요.
                            """,
                    url = "https://www.notion.so/API-ENUM-c65d84ea50a249dd972d7c8c296750ee")
    )
    @AuthRequired
    @GetMapping ("")
    public ResponseEntity<SuccessResponse> findAllByIsAvailableTrue (final @RequestParam(required = false) String categoryName) {

        return SuccessResponse.toResponseEntity(
                BoardSuccessCode.FIND_ALL_AVAILABLE_TRUE,
                boardFacade.findAllByCategoryNameAndIsAvailableTrue(categoryName)
        );
    }

    @Operation(
            summary = "게시판 단건 조회",
            description =
                    """
                    ## [게시판 단건 조회 API]
                    ### 게시판을 조회합니다.
                    ### 게시판의 식별자로 게시판의 정보를 불러옵니다.
                    
                    ## [호출 권한]
                    ### ALL
                    """
    )
    @AuthRequired
    @PostMapping ("/{boardId}")
    public ResponseEntity<SuccessResponse> findByBoardIdAndIsAvailableTrue (final @NotNull @PathVariable Long boardId) {

        return SuccessResponse.toResponseEntity(
                BoardSuccessCode.FIND_AVAILABLE_TRUE,
                boardFacade.findByBoardIdAndIsAvailableTrue(boardId));
    }

    @Operation(
            summary = "게시판 비활성화",
            description =
                    """
                    ## [게시판 비활성화 API]
                    ### 게시판을 비활성화 합니다.
                    
                    ## [호출 권한]
                    ### ALL
                    
                    ## [Exceptions]
                    ### UserErrorCode.NOT_FOUND : 로그인한 유저 정보를 찾을 수 없는 경우 해당 예외를 리턴합니다.
                    ### BoardErrorCode.NOT_FOUND : 게시판 정보를 찾을 수 없는 경우 해당 예외를 리턴합니다.
                    ### BoardErrorCode.IS_NOT_AVAILABLE : 게시판이 이미 비활성화 상태인 경우 해당 예외를 리턴합니다.
                    """
    )
    @AuthRequired (roles = {MANAGER, BARISTA, DEVELOPER})
    @PatchMapping ("/disable/{boardId}")
    public ResponseEntity<SuccessResponse> disable (final @NotNull @AuthUser @Parameter(hidden = true) UserAuthentication authentication,
                                      final @NotNull @PathVariable Long boardId) {

        return SuccessResponse.toResponseEntity(
                BoardSuccessCode.DISABLE,
                boardFacade.disable(authentication.getUserId(), boardId));
    }

    @Operation(
            summary = "게시판 활성화",
            description =
                    """
                    ## [게시판 활성화 API]
                    ### 게시판을 활성화 합니다.
                    
                    ## [호출 권한]
                    ### ALL
                    
                    ## [Exceptions]
                    ### UserErrorCode.NOT_FOUND : 로그인한 유저 정보를 찾을 수 없는 경우 해당 예외를 리턴합니다.
                    ### BoardErrorCode.NOT_FOUND : 게시판 정보를 찾을 수 없는 경우 해당 예외를 리턴합니다.
                    ### BoardErrorCode.IS_AVAILABLE : 게시판이 이미 활성화 상태인 경우 해당 예외를 리턴합니다.
                    """
    )
    @AuthRequired(roles = {MANAGER, BARISTA, DEVELOPER})
    @PatchMapping("/enable/{boardId}")
    public ResponseEntity<SuccessResponse> enable (final @NotNull @AuthUser @Parameter (hidden = true) UserAuthentication authentication,
                                     final @NotNull @PathVariable Long boardId) {

        return SuccessResponse.toResponseEntity(
                BoardSuccessCode.ENABLE,
                boardFacade.enable(authentication.getUserId(), boardId));
    }
}
