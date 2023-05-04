package webling.coffee.backend.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webling.coffee.backend.domain.board.entity.Board;
import webling.coffee.backend.domain.user.entity.User;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByBoardIdAndWriter(Long boardId, User user);
}
