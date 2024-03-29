package webling.coffee.backend.domain.menu.service.core;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.menu.dto.request.MenuRequestDto;
import webling.coffee.backend.domain.menu.dto.response.MenuResponseDto;
import webling.coffee.backend.domain.menu.entity.FavoriteMenu;
import webling.coffee.backend.domain.menu.entity.Menu;
import webling.coffee.backend.domain.menu.repository.favoriteMenu.FavoriteMenuRepository;
import webling.coffee.backend.domain.menu.repository.menu.MenuRepository;
import webling.coffee.backend.domain.menuCategory.entity.MenuCategory;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.global.responses.errors.codes.MenuErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;
import webling.coffee.backend.global.utils.S3Upload;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;

    private final FavoriteMenuRepository favoriteMenuRepository;

    private final S3Upload s3Upload;

    public boolean isDuplicationByMenuName(final @NotBlank String menuName) {
        return menuRepository.existsByMenuName(menuName);
    }

    public Menu create(final @NotNull MenuCategory category,
                       final @NotNull MenuRequestDto.Create request) {
        try {
            Menu entity = Menu.create(request.getMenuImage() != null ? s3Upload.upload(request.getMenuImage()) : null, category, request);
            return menuRepository.save(entity);
        } catch (IOException e) {
            throw new RestBusinessException.Failure(MenuErrorCode.PHOTO_UPLOAD_FAILED);
        }
    }

    @Transactional(readOnly = true)
    public Menu findById(final @NotNull Long id) {

        return menuRepository.findById(id)
                .orElseThrow(() -> new RestBusinessException.NotFound(MenuErrorCode.NOT_FOUND));
    }

    @Transactional (readOnly = true)
    public Menu findByIdAndAvailable (final @NotNull Long id) {
        return menuRepository.findByIdAndAvailable(id)
                .orElseThrow(() -> new RestBusinessException.NotFound(MenuErrorCode.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<MenuResponseDto.Find> findAllAvailable(final @NotNull MenuRequestDto.Search request) {
        return menuRepository.findAllAvailable(request);
    }

    public Menu updateMenuWithCategory(Menu menu, MenuCategory menuCategory, MenuRequestDto.Update request) {
        try {
            return menuRepository.save(Menu.updateWithCategory(request.getMenuImage() != null ? s3Upload.upload(request.getMenuImage()) : null, menu, menuCategory, request));
        } catch (IOException e){
            throw new RestBusinessException.Failure(MenuErrorCode.PHOTO_UPLOAD_FAILED);
        }
    }

    public Menu updateMenu(Menu menu, MenuRequestDto.Update request) {
        try {
            return menuRepository.save(Menu.update(request.getMenuImage() != null ? s3Upload.upload(request.getMenuImage()) : null,menu, request));
        } catch (IOException e){
            throw new RestBusinessException.Failure(MenuErrorCode.PHOTO_UPLOAD_FAILED);
        }
    }

    public Menu soldOut(Menu menu) {
        return Menu.soldOut(menu);
    }

    public Menu restore(Menu menu) {
        return Menu.restore(menu);
    }

    public FavoriteMenu saveFavoriteMenu(final User user, final Menu menu) {

        FavoriteMenu favoriteMenu = favoriteMenuRepository.findByUserAndMenu(user, menu);

        if (favoriteMenu != null) {
            return FavoriteMenu.updateFavorite(favoriteMenu);
        } else {
            return favoriteMenuRepository.save(FavoriteMenu.toEntity(user, menu));
        }
    }

    public List<MenuResponseDto.Find> getFavoriteMenuList(final @NotNull User user) {
        return menuRepository.getFavoriteMenuList(user);
    }
}
