package webling.coffee.backend.domain.user.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webling.coffee.backend.domain.user.dto.request.UserRequestDto;
import webling.coffee.backend.domain.user.service.UserFacade;
import webling.coffee.backend.global.annotation.AuthRequired;
import webling.coffee.backend.global.annotation.AuthUser;
import webling.coffee.backend.global.context.UserAuthentication;
import webling.coffee.backend.global.redis.service.RefreshTokenRedisService;
import webling.coffee.backend.global.responses.success.codes.AuthSuccessCode;
import webling.coffee.backend.global.responses.success.codes.UserSuccessCode;
import webling.coffee.backend.global.responses.success.response.SuccessResponse;

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
            summary = "회원가입 - 관리자 권한",
            description = """
                    ## [회원가입 - 관리자 권한 API]
                    ### 관리자가 카페테리아 어플리케이션에 회원을 등록합니다.
                    
                    ## [호출 권한]
                    ### MANAGER, DEVELOPER
                    
                    ## [Exceptions]
                    ### 1. UserErrorCode.DUPLICATION : 중복되는 이메일 주소의 회원이 존재하는 경우 해당 예외를 리턴합니다.
                    """,
            externalDocs = @ExternalDocumentation(
                    description = """
                            ## [ENUM]
                            ### 노션 링크를 참고해주세요.
                            """,
                    url = "https://www.notion.so/API-ENUM-c65d84ea50a249dd972d7c8c296750ee")
    )
    @AuthRequired (roles = {MANAGER, DEVELOPER})
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> register(final @NotNull @RequestBody UserRequestDto.@Valid Register request) {

        return SuccessResponse.toResponseEntity(
                UserSuccessCode.REGISTER,
                userFacade.register(request)
        );
    }

    @Operation(
            summary = "로그아웃",
            description = """
                    ## [로그아웃 API]
                    ### UserAuthentication 객체를 통해 로그아웃을 진행합니다.
                    """
    )
    @AuthRequired
    @PostMapping("/logout")
    public ResponseEntity<SuccessResponse> logout(final @AuthUser @Parameter(hidden = true) UserAuthentication authentication) {
        refreshTokenRedisService.deleteById(authentication.getEmail());
        return SuccessResponse.toResponseEntity(UserSuccessCode.LOGOUT);
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
    @PatchMapping("/me")
    public ResponseEntity<SuccessResponse> update(final @AuthUser @Parameter(hidden = true) UserAuthentication authentication,
                                                  final @NotNull @RequestBody UserRequestDto.UpdateInfo request) {

        return SuccessResponse.toResponseEntity(
                UserSuccessCode.UPDATE,
                userFacade.update(authentication.getUserId(), request));
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
    @AuthRequired(roles = {DEVELOPER})
    @PatchMapping("/role/{userId}")
    public ResponseEntity<SuccessResponse> updateRole(final @PathVariable Long userId,
                                                             final @NotNull @RequestBody UserRequestDto.UpdateRole request) {

        return SuccessResponse.toResponseEntity(
                UserSuccessCode.UPDATE_ROLE,
                userFacade.updateRole(userId, request));
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
    @GetMapping("")
    public ResponseEntity<SuccessResponse> findAllByIsAvailableTrue() {
        return SuccessResponse.toResponseEntity(
                UserSuccessCode.FIND,
                userFacade.findAllByIsAvailableTrue());
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
    @GetMapping("{userId}")
    public ResponseEntity<SuccessResponse> findById(final @NotNull @PathVariable Long userId) {
        return SuccessResponse.toResponseEntity(
                UserSuccessCode.FIND,
                userFacade.findById(userId));
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
    @AuthRequired(roles = {MANAGER, DEVELOPER})
    @DeleteMapping("{userId}")
    public ResponseEntity<SuccessResponse> delete(final @NotNull @PathVariable Long userId) {
        userFacade.delete(userId);
        return SuccessResponse.toResponseEntity(UserSuccessCode.DELETE);
    }

}
