package webling.coffee.backend.domain.board.service.core;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.board.dto.request.BoardRequestDto;
import webling.coffee.backend.domain.board.entity.Board;
import webling.coffee.backend.domain.board.repository.BoardRepository;
import webling.coffee.backend.domain.user.entity.User;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Board create(final @NotNull User user,
                        final @NotNull BoardRequestDto.Create request) {

        return boardRepository.save(Board.toEntity(user, request));
    }
}
