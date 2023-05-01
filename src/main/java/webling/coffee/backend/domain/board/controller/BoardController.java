package webling.coffee.backend.domain.board.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webling.coffee.backend.domain.board.dto.request.BoardRequestDto;
import webling.coffee.backend.domain.board.dto.response.BoardResponseDto;
import webling.coffee.backend.domain.board.service.BoardFacade;
import webling.coffee.backend.global.annotation.AuthRequired;
import webling.coffee.backend.global.annotation.AuthUser;
import webling.coffee.backend.global.context.UserAuthentication;

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
    public ResponseEntity<BoardResponseDto.Create> create (final @NotNull @Parameter(hidden = true) @AuthUser UserAuthentication authentication,
                                                           final @NotNull @RequestBody BoardRequestDto.Create request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(boardFacade.create(authentication.getUserId(), request));
    }
}
