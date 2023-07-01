package webling.coffee.backend.domain.menuCategory.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webling.coffee.backend.domain.menuCategory.entity.MenuCategory;

public class MenuCategoryResponseDto {

    @Getter
    @Setter
    @Builder
    @Schema(name = "menuCategoryResponseCreate")
    public static class Create {

        private Long categoryId;

        private String categoryName;

        public static Create toDto(MenuCategory menuCategory) {
            return Create.builder()
                    .categoryId(menuCategory.getMenuCategoryId())
                    .categoryName(menuCategory.getCategoryName())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @Schema(name = "menuCategoryResponseUpdate")
    public static class Update {

        private Long categoryId;

        private String categoryName;

        public static Update toDto(MenuCategory menuCategory) {
            return Update.builder()
                    .categoryId(menuCategory.getMenuCategoryId())
                    .categoryName(menuCategory.getCategoryName())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @Schema(name = "menuCategoryResponseDelete")
    public static class Delete {

        private Long categoryId;

        private String categoryName;

        private boolean isAvailable;

        public static Delete toDto (MenuCategory menuCategory) {
            return Delete.builder()
                    .categoryId(menuCategory.getMenuCategoryId())
                    .categoryName(menuCategory.getCategoryName())
                    .isAvailable(menuCategory.isAvailable())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @Schema(name = "menuCategoryResponseFind")
    public static class Find {
        private Long categoryId;

        private String categoryName;

        public static Find toDto (MenuCategory menuCategory) {
            return Find.builder()
                    .categoryId(menuCategory.getMenuCategoryId())
                    .categoryName(menuCategory.getCategoryName())
                    .build();
        }
    }
}
