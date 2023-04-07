package webling.coffee.backend.domain.user.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
                    * email : unique 값 잆니다. 중복일 경우, 예외를 반환합니다.
                    * userRole : 회원의 역할입니다. enum 으로 관리되며 [매니저, 일반회원, 게스트, 개발자] 로 나뉩니다.
                    * team : 팀 이름은 enum 으로 관리됩니다.""",
            externalDocs = @ExternalDocumentation (description = "ENUM 정보", url = "https://www.notion.so/COFFEE_WEBLING-feb6ad0af4c04ffeb104f9fce1849b51")
    )
    @PostMapping("")
    public ResponseEntity<UserResponseDto.Register> register (final @RequestBody UserRequestDto.Register request) {

        return new ResponseEntity<>(userFacade.register(request), HttpStatus.CREATED);
    }

    @Operation(
            summary = "로그인",
            description = """
                    * email : 가입된 회원의 이메일 주소입니다.
                    * password : 가입된 회원의 비밀번호입니다.
                    * 로그인에 성공하면 회원의 이메일과 이름을 반환합니다."""
    )
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto.Login> login (final @RequestBody UserRequestDto.Login request) {

        return ResponseEntity.ok()
                .body(userFacade.login(request));
    }
}
