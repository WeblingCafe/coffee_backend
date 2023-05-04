package webling.coffee.backend.domain.board.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.board.dto.request.BoardRequestDto;
import webling.coffee.backend.domain.board.dto.response.BoardResponseDto;
import webling.coffee.backend.domain.board.entity.Board;
import webling.coffee.backend.domain.board.service.core.BoardService;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.domain.user.service.core.UserService;

import java.util.List;

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

    public BoardResponseDto.Update update(final @NotNull Long boardId,
                                          final @NotNull Long userId,
                                          final @NotNull BoardRequestDto.Update request) {

        User user = userService.findByIdAndIsAvailableTrue(userId);
        Board board = boardService.findByBoardIdAndWriter(boardId, user);

        return BoardResponseDto.Update.toDto(boardService.update(board, request));
    }

    public List<BoardResponseDto.Find> findAllByCategoryNameAndIsAvailableTrue(final @NotNull String categoryName) {
        return boardService.findAllByCategoryNameAndIsAvailableTrue(categoryName);
    }
}
