package webling.coffee.backend.domain.board.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
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

    @Getter
    @Setter
    @Builder
    @Schema (name = "boardResponseUpdate")
    public static class Update {
        private Long boardId;
        private String title;
        private String content;
        private String boardCategory;

        public static Update toDto(final @NotNull Board update) {
            return Update.builder()
                    .boardId(update.getBoardId())
                    .title(update.getTitle())
                    .content(update.getContent())
                    .boardCategory(update.getBoardCategory().name())
                    .build();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    @Schema (name = "boardResponseUpdate")
    public static class Find {
        private Long boardId;
        private String title;
        private String content;
        private boolean isAvailable;

        @QueryProjection
        public Find(Long boardId, String title, String content) {
            this.boardId = boardId;
            this.title = title;
            this.content = content;
        }

        public static Find toDto(final @NotNull Board board) {
            return Find.builder()
                    .boardId(board.getBoardId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .isAvailable(board.isAvailable())
                    .build();
        }
    }
}
