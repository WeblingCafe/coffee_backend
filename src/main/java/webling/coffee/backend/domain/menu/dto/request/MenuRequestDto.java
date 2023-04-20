package webling.coffee.backend.domain.menu.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class MenuRequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Create {
        private String menuName;
        private Long menuCategoryId;
        private Long price;
        private String menuPhotoUrl;
        private boolean hotAvailable;
        private boolean coldAvailable;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Update {
        private String menuName;
        private Long menuCategoryId;
        private Long price;
        private String menuPhotoUrl;
        private boolean hotAvailable;
        private boolean coldAvailable;
    }
}
