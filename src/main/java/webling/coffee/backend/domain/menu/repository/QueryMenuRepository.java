package webling.coffee.backend.domain.menu.repository;

import webling.coffee.backend.domain.menu.dto.response.MenuResponseDto;

import java.util.List;

public interface QueryMenuRepository {

    List<MenuResponseDto.Find> findAllAvailable ();
}
