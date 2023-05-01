package webling.coffee.backend.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webling.coffee.backend.domain.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
