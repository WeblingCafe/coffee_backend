package webling.coffee.backend.domain.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import webling.coffee.backend.domain.board.dto.response.BoardResponseDto;
import webling.coffee.backend.domain.board.entity.Board;

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
}
