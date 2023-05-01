package webling.coffee.backend.domain.board.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webling.coffee.backend.domain.board.entity.Board;

public class BoardResponseDto {

    @Getter
    @Setter
    @Builder
    @Schema (name = "boardResponseCreate")
    public static class Create {
        private Long boardId;
        private String title;
        private String content;
        private String boardCategory;
        private String writer;

        public static Create toDto(final @NotNull Board board) {
            return Create.builder()
                    .boardId(board.getBoardId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .boardCategory(board.getBoardCategory().name())
                    .writer(board.getWriter().getNickname())
                    .build();
        }
    }
}
