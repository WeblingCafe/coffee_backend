package webling.coffee.backend.domain.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.util.StringUtils;
import webling.coffee.backend.domain.board.dto.request.BoardRequestDto;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.global.base.BaseTime;
import webling.coffee.backend.global.enums.BoardCategory;

@Getter
@Setter(AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@Table(name = "board_mst")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Board extends BaseTime {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long boardId;

    private String title;

    private String content;

    private boolean isAvailable;

    @Enumerated (EnumType.STRING)
    private BoardCategory boardCategory;

    @ManyToOne (fetch = FetchType.LAZY)
    private User writer;

    public static Board toEntity(final @NotNull User user,
                                 final @NotNull BoardRequestDto.Create request) {

        return Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .isAvailable(true)
                .boardCategory(BoardCategory.of(request.getBoardCategory()))
                .writer(user)
                .build();
    }

    public static Board update(final @NotNull Board board,
                               final @NotNull BoardRequestDto.Update request) {

        if (StringUtils.hasText(request.getTitle()))
            board.setTitle(request.getTitle());

        if (StringUtils.hasText(request.getContent()))
            board.setContent(request.getContent());

        return board;
    }

    public static void disable(final @NotNull Board board) {
        board.setAvailable(false);
    }

    public static void enable(final @NotNull Board board) {
        board.setAvailable(true);
    }
}
