package webling.coffee.backend.domain.menuCategory.dto.request;

import lombok.Getter;
import lombok.Setter;

public class MenuCategoryRequestDto {

    @Getter
    @Setter
    public static class Create {
        private String categoryName;

    }

    @Getter
    @Setter
    public static class Update {
        private String categoryName;

    }
}
