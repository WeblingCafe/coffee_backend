package webling.coffee.backend.domain.menu.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webling.coffee.backend.domain.menu.entity.FavoriteMenu;
import webling.coffee.backend.domain.menu.entity.Menu;
import webling.coffee.backend.domain.menuCategory.entity.MenuCategory;

import java.util.List;
import java.util.stream.Collectors;

public class MenuResponseDto {

    @Getter
    @Setter
    @Builder
    @Schema(name = "MenuResponseCreate")
    public static class Create {
        private Long menuId;

        private String menuName;

        private String menuCategoryName;

        private Long price;

        private String menuPhotoUrl;

        private boolean hotAvailable;

        private boolean coldAvailable;

        public static Create toDto(final @NotNull Menu menu) {
            return Create.builder()
                    .menuId(menu.getMenuId())
                    .menuName(menu.getMenuName())
                    .menuCategoryName(menu.getCategory().getCategoryName())
                    .price(menu.getPrice())
                    .menuPhotoUrl(menu.getMenuPhotoUrl())
                    .hotAvailable(menu.isHotAvailable())
                    .coldAvailable(menu.isColdAvailable())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @Schema(name = "MenuResponseUpdate")
    public static class Update {

        private Long menuId;

        private String menuName;

        private String menuCategoryName;

        private Long price;

        private String menuPhotoUrl;

        private boolean hotAvailable;

        private boolean coldAvailable;

        public static Update toDto(Menu menu) {
            return Update.builder()
                    .menuId(menu.getMenuId())
                    .menuName(menu.getMenuName())
                    .menuCategoryName(menu.getCategory().getCategoryName())
                    .price(menu.getPrice())
                    .menuPhotoUrl(menu.getMenuPhotoUrl())
                    .hotAvailable(menu.isHotAvailable())
                    .coldAvailable(menu.isColdAvailable())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @Schema(name = "MenuResponseFind")
    public static class Find {

        private Long menuId;

        private String menuName;

        private String menuCategoryName;

        private Long price;

        private String menuPhotoUrl;

        private boolean hotAvailable;

        private boolean coldAvailable;
        public static Find toDto(Menu menu) {
            return Find.builder()
                    .menuId(menu.getMenuId())
                    .menuName(menu.getMenuName())
                    .menuCategoryName(menu.getCategory().getCategoryName())
                    .price(menu.getPrice())
                    .menuPhotoUrl(menu.getMenuPhotoUrl())
                    .hotAvailable(menu.isHotAvailable())
                    .coldAvailable(menu.isColdAvailable())
                    .build();
        }

        @QueryProjection
        public Find(Long menuId, String menuName, String menuCategoryName, Long price, String menuPhotoUrl, boolean hotAvailable, boolean coldAvailable) {
            this.menuId = menuId;
            this.menuName = menuName;
            this.menuCategoryName = menuCategoryName;
            this.price = price;
            this.menuPhotoUrl = menuPhotoUrl;
            this.hotAvailable = hotAvailable;
            this.coldAvailable = coldAvailable;
        }
    }

    @Getter
    @Setter
    @Builder
    @Schema(name = "MenuResponseFindByCategory")
    public static class FindByCategory {
        private Long categoryId;
        private String menuCategoryName;
        private List<Find> menuDtoList;

        public static FindByCategory toDto (MenuCategory menuCategory) {
            return FindByCategory.builder()
                    .categoryId(menuCategory.getMenuCategoryId())
                    .menuCategoryName(menuCategory.getCategoryName())
                    .menuDtoList(menuCategory.getMenuList().stream().map(Find::toDto).collect(Collectors.toList()))
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @Schema(name = "MenuResponseSoldOut")
    public static class SoldOut {
        private Long menuId;
        private String menuName;
        private boolean isAvailable;

        public static SoldOut toDto(Menu menu) {
            return SoldOut.builder()
                    .menuId(menu.getMenuId())
                    .menuName(menu.getMenuName())
                    .isAvailable(menu.isAvailable())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @Schema(name = "MenuResponseRestore")
    public static class Restore {
        private Long menuId;
        private String menuName;
        private boolean isAvailable;

        public static Restore toDto(Menu menu) {
            return Restore.builder()
                    .menuId(menu.getMenuId())
                    .menuName(menu.getMenuName())
                    .isAvailable(menu.isAvailable())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @Schema (name = "MenuResponseFavorite")
    public static class Favorite {
        private String MenuName;
        private String userEmail;
        private boolean favorite;

        public static Favorite toDto (FavoriteMenu favoriteMenu) {
            return Favorite.builder()
                    .MenuName(favoriteMenu.getMenu().getMenuName())
                    .userEmail(favoriteMenu.getUser() .getEmail())
                    .favorite(favoriteMenu.isFavorite())
                    .build();
        }
    }
}
