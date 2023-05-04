package webling.coffee.backend.domain.board.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import webling.coffee.backend.domain.board.dto.response.BoardResponseDto;
import webling.coffee.backend.domain.board.dto.response.QBoardResponseDto_Find;
import webling.coffee.backend.global.enums.BoardCategory;

import java.util.List;

import static webling.coffee.backend.domain.board.entity.QBoard.board;

@RequiredArgsConstructor
public class QueryBoardRepositoryImpl implements QueryBoardRepository{

    private final JPAQueryFactory jpaQueryFactory ;

    @Override
    public List<BoardResponseDto.Find> findAllByCategoryNameAndIsAvailableTrue(String categoryName) {
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
                .fetch();
    }

    private BooleanExpression boardCategoryEq(String categoryName) {
        return StringUtils.hasText(categoryName) ? board.boardCategory.eq(BoardCategory.of(categoryName)) : null;
    }
}
