package webling.coffee.backend.domain.board.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.board.dto.request.BoardRequestDto;
import webling.coffee.backend.domain.board.dto.response.BoardResponseDto;
import webling.coffee.backend.domain.board.service.core.BoardService;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.domain.user.service.core.UserService;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardFacade {

    private final BoardService boardService;

    private final UserService userService;

    public BoardResponseDto.Create create(final @NotNull Long userId,
                                          final @NotNull BoardRequestDto.Create request) {

        User user = userService.findByIdAndIsAvailableTrue(userId);

        return BoardResponseDto.Create.toDto(boardService.create(user, request));
    }

}
