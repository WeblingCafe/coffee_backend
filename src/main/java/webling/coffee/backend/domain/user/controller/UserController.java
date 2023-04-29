package webling.coffee.backend.domain.user.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webling.coffee.backend.domain.user.dto.request.UserRequestDto;
import webling.coffee.backend.domain.user.dto.response.UserResponseDto;
import webling.coffee.backend.domain.user.service.UserFacade;
import webling.coffee.backend.global.annotation.AuthRequired;
import webling.coffee.backend.global.annotation.AuthUser;
import webling.coffee.backend.global.context.UserAuthentication;
import webling.coffee.backend.global.redis.service.RefreshTokenRedisService;

import static webling.coffee.backend.global.enums.UserRole.DEVELOPER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserFacade userFacade;

    private final RefreshTokenRedisService refreshTokenRedisService;

    @Operation(
            summary = "로그아웃",
            description = """
                    ## [로그아웃 API]
                    ### UserAuthentication 객체를 통해 로그아웃을 진행합니다.
                    """
    )
    @AuthRequired
    @PostMapping("/logout")
    public ResponseEntity<?> logout (final @AuthUser @Parameter(hidden = true) UserAuthentication authentication) {

        /**
         * 백엔드 로그아웃 설계
         * 1. redis 에 저장된 refresh token 값을 logout 값으로 변경?
         * 2. 프론트에 맡기기..?
         */

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "내 정보 수정",
            description = """
                    ## [내 정보 수정 API]
                    ### UserAuthentication 객체를 통해 현재 로그인 한 회원의 정보를 바탕으로 정보를 수정합니다.
                    ### 변경가능한 내용은 request 필드를 참고해주세요.
                    
                    ## [호출 권한]
                    ### 접속한 UserAuthentication 객체
                    
                    ## [Exceptions]
                    ### UserErrorCode.NOT_FOUND : UserAuthentication 을 바탕으로 얻은 시퀀스로 조회한 유저가 없을 경우 예외를 리턴합니다.
                    """
    )
    @AuthRequired
    @PatchMapping ("/me")
    public ResponseEntity<UserResponseDto.Update> update (final @AuthUser @Parameter(hidden = true) UserAuthentication authentication,
                                                          final @NotNull @RequestBody UserRequestDto.UpdateInfo request) {

        return ResponseEntity.ok()
                .body(userFacade.update(authentication.getUserId(), request));
    }

    @Operation(
            summary = "권한 부여",
            description = """
                    ## [권한 부여 API]
                    ### 가입한 유저에게 권한을 부여합니다.
                    ### 부여할 수 있는 권한의 ENUM 값은 노션 링크를 참고해주세요.
                    
                    ## [호출 권한]
                    ### DEVELOPER
                    
                    ## [Exceptions]
                    ### UserErrorCode.NOT_FOUND : 식별자인 시퀀스로 조회한 유저가 없을 경우 예외를 리턴합니다.
                    ### EnumValueErrorCode.USER_ROLE_VALUE_INVALID : 부여할 권한이 ENUM 값에서 찾을 수 없을 경우 예외를 리턴합니다.
                    """,
            externalDocs = @ExternalDocumentation(
                    description = """
                            ## [ENUM]
                            ### 노션 링크를 참고해주세요.
                            """,
                    url = "https://www.notion.so/API-ENUM-c65d84ea50a249dd972d7c8c296750ee")
    )
    @AuthRequired (roles = {DEVELOPER})
    @PatchMapping ("/role/{id}")
    public ResponseEntity<UserResponseDto.Update> updateRole (final @PathVariable Long id,
                                                              final @NotNull @RequestBody UserRequestDto.UpdateRole request) {

        return ResponseEntity.ok()
                .body(userFacade.updateRole(id, request));
    }

}
