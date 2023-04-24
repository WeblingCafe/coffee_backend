package webling.coffee.backend.domain.authentication.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webling.coffee.backend.domain.authentication.service.AuthenticationFacade;
import webling.coffee.backend.domain.user.dto.request.UserRequestDto;
import webling.coffee.backend.domain.user.dto.response.UserResponseDto;
import webling.coffee.backend.domain.user.service.UserFacade;
import webling.coffee.backend.global.annotation.AuthRequired;
import webling.coffee.backend.global.redis.service.RefreshTokenRedisService;
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
            summary = "로그인",
            description = """
                    ## [로그인 API]
                    ### 이메일과 패스워드를 통해 로그인을 진행합니다.
                    """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = """
                            ## [REQUEST BODY]
                            ### email : 가입된 회원의 이메일 주소입니다.
                            ### password : 가입된 회원의 비밀번호입니다.
                            ### 로그인에 성공하면 회원의 이메일과 이름을 반환합니다.
                            """
            )
    )
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto.Login> login (final @RequestBody UserRequestDto.Login request) {

        UserResponseDto.Login memberDto = authenticationFacade.login(request);

        return ResponseEntity.ok()
                .headers(jwtUtils.getAuthHeaders(memberDto.getUserId(), memberDto.getEmail()))
                .body(memberDto);
    }



    @Operation(
            summary = "회원가입",
            description = """
                    ## [회원가입 API]
                    ### 회원가입을 진행합니다.
                    """,
            externalDocs = @ExternalDocumentation(
                    description = """
                            ## [ENUM]
                            ### 노션 링크를 참고해주세요.
                            """,
                    url = "https://www.notion.so/API-ENUM-c65d84ea50a249dd972d7c8c296750ee"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = """
                            ## [REQUEST BODY]
                            ### email : unique 값 잆니다. 중복일 경우, 예외를 반환합니다.
                            ### userRole : 회원의 역할입니다. enum 으로 관리되며 [매니저, 일반회원, 게스트, 개발자] 로 나뉩니다.
                            ### team : 팀 이름은 enum 으로 관리됩니다.
                            """
            )
    )
    @PostMapping("")
    public ResponseEntity<UserResponseDto.Register> register (@RequestBody UserRequestDto.@Valid Register request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userFacade.register(request));
    }
}
