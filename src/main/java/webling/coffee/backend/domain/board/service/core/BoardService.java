package webling.coffee.backend.domain.board.service.core;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.board.dto.request.BoardRequestDto;
import webling.coffee.backend.domain.board.dto.response.BoardResponseDto;
import webling.coffee.backend.domain.board.entity.Board;
import webling.coffee.backend.domain.board.repository.BoardRepository;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.global.responses.errors.codes.BoardErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Board create(final @NotNull User user,
                        final @NotNull BoardRequestDto.Create request) {

        return boardRepository.save(Board.toEntity(user, request));
    }

    public Board findByBoardIdAndWriter(final @NotNull Long boardId,
                                                  final @NotNull User user) {
        return boardRepository.findByBoardIdAndWriter(boardId, user)
                .orElseThrow(() -> new RestBusinessException.NotFound(BoardErrorCode.NOT_FOUND));
    }

    public Board update(final @NotNull Board board,
                        final @NotNull BoardRequestDto.Update request) {
        return boardRepository.save(Board.update(board, request));
    }

    public List<BoardResponseDto.Find> findAllByCategoryNameAndIsAvailableTrue(String categoryName, final @NotNull BoardRequestDto.Search request) {
        return boardRepository.findAllByCategoryNameAndIsAvailableTrue(categoryName, request);
    }

    public BoardResponseDto.Find findByBoardIdAndIsAvailableTrue(final @NotNull Long boardId) {
        return boardRepository.findByIdAndIsAvailableTrue(boardId);
    }

    public Board disable(final @NotNull Board board) {
        return Board.disable(board);
    }

    public Board enable(final @NotNull Board board) {
        return Board.enable(board);
    }
}
