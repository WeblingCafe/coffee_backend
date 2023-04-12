package webling.coffee.backend.domain.board.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
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


}
