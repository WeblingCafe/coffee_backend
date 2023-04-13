package webling.coffee.backend.domain.user.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webling.coffee.backend.domain.user.dto.request.UserRequestDto;
import webling.coffee.backend.domain.user.dto.response.UserResponseDto;
import webling.coffee.backend.domain.user.service.UserFacade;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserFacade userFacade;

    @Operation(
            summary = "회원가입",
            description = """
                    ## [회원가입 API]
                    ### 회원가입을 진행합니다.
                    """,
            externalDocs = @ExternalDocumentation (
                    description = """
                            # [ENUM]
                            ## 노션 링크를 참고해주세요.
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
    public ResponseEntity<UserResponseDto.Register> register (final @RequestBody UserRequestDto.Register request) {

        return new ResponseEntity<>(userFacade.register(request), HttpStatus.CREATED);
    }

}
