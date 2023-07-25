package webling.coffee.backend.domain.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webling.coffee.backend.domain.board.dto.response.BoardResponseDto;
import webling.coffee.backend.domain.board.entity.Board;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class BoardRequestDto {

    @Getter
    @Setter
    @Schema (name = "boardRequestCreate")
    public static class Create {

        @NotNull
        private String title;
        @NotNull
        private String content;
        private String boardCategory;
    }

    @Getter
    @Setter
    @Schema (name = "boardRequestUpdate")
    public static class Update {

        @NotNull
        private String title;
        @NotNull
        private String content;
    }

    @Getter
    @Setter
    @Builder
    @Schema (name = "boardRequestSearch")
    public static class Search {

        private static final int MAX_SIZE = 200;

        @Builder.Default
        private Integer page = 1;

        @Builder.Default
        private Integer size = 10;

        public long getOffSet () {
            return (long) (max(1, page) -1) * min(size, MAX_SIZE);
        }
    }
}
