package webling.coffee.backend.domain.menu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
public class MenuRequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @Schema (name = "menuRequestCreate")
    public static class Create {

        @NotNull
        private String menuName;

        @NotNull
        private Long menuCategoryId;

        @NotNull
        private Long price;

        private MultipartFile menuImage;

        @NotNull
        private boolean hotAvailable;

        @NotNull
        private boolean coldAvailable;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @Schema (name = "menuRequestUpdate")
    public static class Update {
        private String menuName;
        private Long menuCategoryId;
        private Long price;
        private MultipartFile menuImage;
        private boolean hotAvailable;
        private boolean coldAvailable;
    }
}
