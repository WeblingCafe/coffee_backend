package webling.coffee.backend.domain.board.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import webling.coffee.backend.domain.board.dto.request.BoardRequestDto;
import webling.coffee.backend.domain.board.dto.response.BoardResponseDto;
import webling.coffee.backend.domain.board.dto.response.QBoardResponseDto_Find;
import webling.coffee.backend.global.enums.BoardCategory;

import java.util.List;

import static webling.coffee.backend.domain.board.entity.QBoard.board;

@RequiredArgsConstructor
public class QueryBoardRepositoryImpl implements QueryBoardRepository{

    private final JPAQueryFactory jpaQueryFactory ;

    @Override
    public List<BoardResponseDto.Find> findAllByCategoryNameAndIsAvailableTrue(String categoryName, final @NotNull BoardRequestDto.Search request) {
        return jpaQueryFactory.select(new QBoardResponseDto_Find(
                board.boardId,
                board.title,
                board.content
        ))
                .from(board)
                .where(
                    board.isAvailable.isTrue(),
                    boardCategoryEq(categoryName)
                )
                .limit(request.getSize())
                .offset(request.getOffSet())
                .fetch();
    }

    @Override
    public BoardResponseDto.Find findByIdAndIsAvailableTrue(final Long boardId) {
        return jpaQueryFactory.select(new QBoardResponseDto_Find(
                board.boardId,
                board.title,
                board.content
        ))
                .from(board)
                .where(
                        board.boardId.eq(boardId),
                        board.isAvailable.isTrue()
                )
                .fetchFirst()
                ;
    }

    private BooleanExpression boardCategoryEq(String categoryName) {
        return StringUtils.hasText(categoryName) ? board.boardCategory.eq(BoardCategory.of(categoryName)) : null;
    }
}
