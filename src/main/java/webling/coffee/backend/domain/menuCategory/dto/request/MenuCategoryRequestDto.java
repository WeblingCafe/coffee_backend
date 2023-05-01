package webling.coffee.backend.domain.menuCategory.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class MenuCategoryRequestDto {

    @Getter
    @Setter
    @Schema(name = "menuCategoryRequestCreate")
    public static class Create {
        private String categoryName;

    }

    @Getter
    @Setter
    @Schema(name = "menuCategoryRequestUpdate")
    public static class Update {
        private String categoryName;
    }
}
