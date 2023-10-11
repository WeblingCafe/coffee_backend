package webling.coffee.backend.domain.user.controller;

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
import webling.coffee.backend.global.responses.success.codes.UserSuccessCode;
import webling.coffee.backend.global.responses.success.response.SuccessResponse;

import static webling.coffee.backend.global.enums.UserRole.DEVELOPER;
import static webling.coffee.backend.global.enums.UserRole.MANAGER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController implements UserSwagger {

    private final UserFacade userFacade;
    private final RefreshTokenRedisService refreshTokenRedisService;

    @AuthRequired (roles = {MANAGER, DEVELOPER})
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> register(final @NotNull @RequestBody UserRequestDto.@Valid Register request) {

        return SuccessResponse.toResponseEntity(
                UserSuccessCode.REGISTER,
                userFacade.register(request)
        );
    }

    @AuthRequired
    @PostMapping("/logout")
    public ResponseEntity<SuccessResponse> logout(final @AuthUser @Parameter(hidden = true) UserAuthentication authentication) {
        refreshTokenRedisService.deleteById(authentication.getEmail());
        return SuccessResponse.toResponseEntity(UserSuccessCode.LOGOUT);
    }

    @AuthRequired
    @PatchMapping("/me")
    public ResponseEntity<SuccessResponse> update(final @AuthUser @Parameter(hidden = true) UserAuthentication authentication,
                                                  final @NotNull @RequestBody UserRequestDto.UpdateInfo request) {

        return SuccessResponse.toResponseEntity(
                UserSuccessCode.UPDATE,
                userFacade.update(authentication.getUserId(), request));
    }

    @AuthRequired(roles = {DEVELOPER})
    @PatchMapping("/role/{userId}")
    public ResponseEntity<SuccessResponse> updateRole(final @PathVariable Long userId,
                                                             final @NotNull @RequestBody UserRequestDto.UpdateRole request) {

        return SuccessResponse.toResponseEntity(
                UserSuccessCode.UPDATE_ROLE,
                userFacade.updateRole(userId, request));
    }

    @AuthRequired
    @GetMapping("")
    public ResponseEntity<SuccessResponse> findAllByIsAvailableTrue() {
        return SuccessResponse.toResponseEntity(
                UserSuccessCode.FIND,
                userFacade.findAllByIsAvailableTrue());
    }

    @AuthRequired
    @GetMapping("{userId}")
    public ResponseEntity<SuccessResponse> findById(final @NotNull @PathVariable Long userId) {
        return SuccessResponse.toResponseEntity(
                UserSuccessCode.FIND,
                userFacade.findById(userId));
    }

    @AuthRequired(roles = {MANAGER, DEVELOPER})
    @DeleteMapping("{userId}")
    public ResponseEntity<SuccessResponse> delete(final @NotNull @PathVariable Long userId) {
        userFacade.delete(userId);
        return SuccessResponse.toResponseEntity(UserSuccessCode.DELETE);
    }

}
