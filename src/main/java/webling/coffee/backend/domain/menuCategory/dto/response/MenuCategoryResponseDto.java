package webling.coffee.backend.domain.menuCategory.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webling.coffee.backend.domain.menuCategory.entity.MenuCategory;

public class MenuCategoryResponseDto {

    @Getter
    @Setter
    @Builder
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
}
