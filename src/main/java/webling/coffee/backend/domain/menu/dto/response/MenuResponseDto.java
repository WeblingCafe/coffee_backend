package webling.coffee.backend.domain.menu.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webling.coffee.backend.domain.menu.entity.Menu;

public class MenuResponseDto {

    @Getter
    @Setter
    @Builder
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
    public static class Update {

        public static Update toDto(Menu update) {
            return null;
        }
    }

    public static class SoldOut {
        public static SoldOut toDto(Menu soldOut) {
            return null;
        }
    }
}
