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

import java.util.List;

import static webling.coffee.backend.global.enums.UserRole.DEVELOPER;
import static webling.coffee.backend.global.enums.UserRole.MANAGER;

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

        refreshTokenRedisService.deleteById(authentication.getEmail());

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
    @PatchMapping ("/role/{userId}")
    public ResponseEntity<UserResponseDto.Update> updateRole (final @PathVariable Long userId,
                                                              final @NotNull @RequestBody UserRequestDto.UpdateRole request) {

        return ResponseEntity.ok()
                .body(userFacade.updateRole(userId, request));
    }

    @Operation(
            summary = "가입 유저 전체 조회",
            description = """
                    ## [가입 유저 전체 조회 API]
                    ### 가입된 유저 전체 정보를 조회합니다.
                    ### 현재 유저 상태가 IsAvailable 이 true 인 유저들의 리스트를 리턴합니다.
                    ### 만약 IsAvailable 이 true 인 유저들이 아무도 없다면, 빈 리스트를 반환합니다.
                    
                    ## [호출 권한]
                    ### ALL
                    """
    )
    @AuthRequired
    @GetMapping ("")
    public ResponseEntity<List<UserResponseDto.Find>> findAllByIsAvailableTrue () {
        return ResponseEntity.ok()
                .body(userFacade.findAllByIsAvailableTrue());
    }

    @Operation(
            summary = "유저 조회",
            description = """
                    ## [유저 조회 API]
                    ### 가입된 유저 정보를 조회합니다.
                    ### 현재 유저 상태가 IsAvailable 이 true 인 유저 리스트를 리턴합니다.
                    ### User 의 식별자인 userId 값을 pathVariable 로 받아 식별합니다.
                    
                    ## [호출 권한]
                    ### ALL
                    
                    ###[Exceptions]
                    ### UserErrorCode.NOT_FOUND : 식별자인 시퀀스로 조회한 유저가 없을 경우 예외를 리턴합니다.
                    """
    )
    @AuthRequired
    @GetMapping ("{userId}")
    public ResponseEntity<UserResponseDto.Find> findById (final @NotNull @PathVariable Long userId) {
        return ResponseEntity.ok()
                .body(userFacade.findById(userId));
    }

    @Operation(
            summary = "유저 삭제 (비활성화)",
            description = """
                    ## [유저 삭제 (비활성화) API]
                    ### 유저를 비활성화 시킵니다. (isAvailable -> false 처리)
                    
                    ## [호출 권한]
                    ### MANAGER, DEVELOPER
                    
                    ###[Exceptions]
                    ### UserErrorCode.NOT_FOUND : 식별자인 시퀀스로 조회한 유저가 없을 경우 예외를 리턴합니다.
                    """
    )
    @AuthRequired (roles = {MANAGER, DEVELOPER})
    @DeleteMapping ("{userId}")
    public ResponseEntity<?> delete (final @NotNull @PathVariable Long userId) {

        userFacade.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
