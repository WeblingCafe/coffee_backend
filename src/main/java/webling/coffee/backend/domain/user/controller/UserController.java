package webling.coffee.backend.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "회원가입",
            description = "1. email : unique 값 잆니다. 중복일 경우, 예외를 반환합니다.\n" +
                    "2. isManager : true 일경우 관리자, false 일경우 일반 회원입니다.\n" +
                    "3. team : 팀 이름은 enum 으로 관리되며, 다음과 같습니다.")
    @PostMapping("")
    public ResponseEntity<UserResponseDto.Register> register (final @RequestBody UserRequestDto.Register request) {

        return new ResponseEntity<>(userFacade.register(request), HttpStatus.CREATED);
    }
}
