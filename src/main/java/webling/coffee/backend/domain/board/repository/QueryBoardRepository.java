package webling.coffee.backend.domain.board.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import webling.coffee.backend.domain.board.dto.request.BoardRequestDto;
import webling.coffee.backend.domain.board.dto.response.BoardResponseDto;

import java.util.List;

public interface QueryBoardRepository {
    List<BoardResponseDto.Find> findAllByCategoryNameAndIsAvailableTrue(final @NotBlank String categoryName, final @NotNull BoardRequestDto.Search request);

    BoardResponseDto.Find findByIdAndIsAvailableTrue(final @NotNull Long boardId);

}
