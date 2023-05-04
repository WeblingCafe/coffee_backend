package webling.coffee.backend.domain.board.repository;

import jakarta.validation.constraints.NotBlank;
import webling.coffee.backend.domain.board.dto.response.BoardResponseDto;

import java.util.List;

public interface QueryBoardRepository {
    List<BoardResponseDto.Find> findAllByCategoryNameAndIsAvailableTrue(final @NotBlank String categoryName);
}
