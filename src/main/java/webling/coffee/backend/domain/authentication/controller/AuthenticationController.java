package webling.coffee.backend.domain.authentication.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webling.coffee.backend.domain.authentication.service.AuthenticationFacade;
import webling.coffee.backend.domain.user.dto.request.UserRequestDto;
import webling.coffee.backend.domain.user.dto.response.UserResponseDto;
import webling.coffee.backend.domain.user.service.UserFacade;
import webling.coffee.backend.global.responses.success.codes.AuthSuccessCode;
import webling.coffee.backend.global.responses.success.response.SuccessResponse;
import webling.coffee.backend.global.utils.JwtUtils;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthenticationController {

    private final AuthenticationFacade authenticationFacade;
    private final UserFacade userFacade;
    private final JwtUtils jwtUtils;

    @Operation(
            summary = "ACCESS TOKEN 재발급",
            description = """
                    ## [ACCESS TOKEN 재발급 API]
                    ### REFRESH TOKEN 으로 ACCESS TOKEN 을 재발급합니다.
                    
                    ## [Exceptions]
                    ### 1. AuthenticationErrorCode.REFRESH_TOKEN_NOT_FOUND : email 로 조회하여 Redis 에 저장된 Refresh Token 찾지 못할 경우 해당 예외를 리턴합니다.
                    ### 2. AuthenticationErrorCode.INVALID_REFRESH_TOKEN : Redis 에 저장된 Refresh Token 과 일치하지 않을 경우 해당 예외를 리턴합니다.
                    """
    )
    @PostMapping("/retrieve-token")
    public ResponseEntity<SuccessResponse> retrieveAccessToken (final @RequestBody UserRequestDto.Token request) {

        return SuccessResponse
                .toResponseEntity(
                        AuthSuccessCode.RETRIEVE_ACCESS_TOKEN,
                        authenticationFacade.retrieveAccessToken(request.getRefreshToken())
                );
    }

    @Operation(
            summary = "로그인",
            description = """
                    ## [로그인 API]
                    ### 이메일과 패스워드를 통해 로그인을 진행합니다.
                    
                    ## [Exceptions]
                    ### 1. UserErrorCode.NOT_FOUND : 로그인을 시도하는 계정을 찾을 수 없을 경우 해당 예외를 리턴합니다.
                    ### 2. UserErrorCode.PASSWORD_MISMATCH : 비밀번호가 일치하지 않을 경우 해당 예외를 리턴합니다.
                    """
    )
    @PostMapping("/login")
    public ResponseEntity<SuccessResponse> login (final @RequestBody UserRequestDto.Login request) {

        UserResponseDto.Login memberDto = authenticationFacade.login(request);

        return SuccessResponse
                .toResponseEntity(
                        jwtUtils.getAuthHeaders(memberDto.getUserId(), memberDto.getEmail(), memberDto.getRefreshToken()),
                        AuthSuccessCode.LOGIN,
                        memberDto
                        );
    }

    @Operation(
            summary = "회원가입",
            description = """
                    ## [회원가입 API]
                    ### 회원가입을 진행합니다.
                    ### 기본으로 회원가입 된 회원의 권한은 EMPLOYEE 로 고정됩니다.
                    
                    ## [Exceptions]
                    ### 1. UserErrorCode.DUPLICATION : 중복되는 이메일 주소의 회원이 존재하는 경우 해당 예외를 리턴합니다.
                    ### 2. EnumValueErrorCode.TEAM_VALUE_INVALID : 가입하려는 회원의 팀 정보가 enum 값으로 없을 경우 해당 예외를 리턴합니다.
                    """,
            externalDocs = @ExternalDocumentation(
                    description = """
                            ## [ENUM]
                            ### 노션 링크를 참고해주세요.
                            """,
                    url = "https://www.notion.so/API-ENUM-c65d84ea50a249dd972d7c8c296750ee")
    )
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> register (@RequestBody UserRequestDto.@Valid Register request) {

        return SuccessResponse.toResponseEntity(
                AuthSuccessCode.REGISTER,
                userFacade.register(request));
    }
}
