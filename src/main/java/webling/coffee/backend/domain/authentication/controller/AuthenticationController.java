package webling.coffee.backend.domain.authentication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webling.coffee.backend.domain.authentication.service.AuthenticationFacade;
import webling.coffee.backend.domain.user.dto.request.UserRequestDto;
import webling.coffee.backend.domain.user.dto.response.UserResponseDto;
import webling.coffee.backend.global.utils.JwtUtils;

@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/v1/auth")
public class AuthenticationController {

    private final AuthenticationFacade authenticationFacade;

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
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "회원가입 성공",
                            content = @Content(schema = @Schema(implementation = UserResponseDto.Login.class))),
                    @ApiResponse(
                            responseCode = "U001",
                            description = "USER_NOT_FOUND, 로그인에 시도한 email 로 가입된 회원을 찾을 수 없습니다."),
                    @ApiResponse(
                            responseCode = "U003",
                            description = "PASSWORD_MISMATCH, 비밀번호가 일치하지 않습니다.")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto.Login> login (final @RequestBody UserRequestDto.Login request) {

        UserResponseDto.Login memberDto = authenticationFacade.login(request);

        return ResponseEntity.ok()
                .headers(jwtUtils.getAuthHeaders(memberDto.getUserId(), memberDto.getEmail()))
                .body(memberDto);
    }
}
