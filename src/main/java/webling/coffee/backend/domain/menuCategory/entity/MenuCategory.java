package webling.coffee.backend.domain.menuCategory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.util.StringUtils;
import webling.coffee.backend.domain.menu.entity.Menu;
import webling.coffee.backend.domain.menuCategory.dto.request.MenuCategoryRequestDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@Table(name = "menu_category_mst")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuCategory {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long menuCategoryId;

    @Column(unique = true)
    private String categoryName;

    private boolean isAvailable;

    @OneToMany(mappedBy = "category")
    private List<Menu> menuList = new ArrayList<>();

    public static MenuCategory create(final @NotNull MenuCategoryRequestDto.Create request) {
        return MenuCategory.builder()
                .categoryName(request.getCategoryName())
                .isAvailable(true)
                .build();
    }

    public static MenuCategory update(final @NotNull MenuCategory menuCategory, final @NotNull MenuCategoryRequestDto.Update request) {

        if (StringUtils.hasText(request.getCategoryName()))
            menuCategory.setCategoryName(request.getCategoryName());

        return menuCategory;
    }
}
