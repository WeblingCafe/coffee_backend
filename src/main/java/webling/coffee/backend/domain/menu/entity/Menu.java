package webling.coffee.backend.domain.menu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.util.StringUtils;
import webling.coffee.backend.domain.menu.dto.request.MenuRequestDto;
import webling.coffee.backend.domain.menuCategory.entity.MenuCategory;
import webling.coffee.backend.global.base.BaseTime;

@Getter
@Setter(AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@Table(name = "menu_mst")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Menu extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_ID")
    private Long menuId;

    @NotNull
    @Column(unique = true)
    private String menuName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "CATEGORY_ID")
    private MenuCategory category;

    @NotNull
    private Long price;
    private String menuPhotoUrl;
    private boolean hotAvailable;
    private boolean coldAvailable;
    private boolean isAvailable;

    public static Menu create(String menuPhotoUrl, MenuCategory category, MenuRequestDto.Create request) {
        return Menu.builder()
                .menuName(request.getMenuName())
                .category(category)
                .price(request.getPrice())
                .menuPhotoUrl(menuPhotoUrl)
                .hotAvailable(request.isHotAvailable())
                .coldAvailable(request.isColdAvailable())
                .isAvailable(true)
                .build();
    }

    public static Menu update (String menuPhotoUrl, Menu menu, MenuRequestDto.Update request) {

        if (StringUtils.hasText(request.getMenuName()))
            menu.setMenuName(request.getMenuName());

        if (request.getPrice() != null)
            menu.setPrice(request.getPrice());

        if (StringUtils.hasText(menuPhotoUrl))
            menu.setMenuPhotoUrl(menuPhotoUrl);

        menu.setColdAvailable(request.isColdAvailable());

        menu.setHotAvailable(request.isHotAvailable());

        return menu;
    }

    public static Menu updateWithCategory(String menuPhotoUrl, Menu menu, MenuCategory menuCategory, MenuRequestDto.Update request) {

        Menu updateMenu = Menu.update(menuPhotoUrl, menu, request);

        updateMenu.setCategory(menuCategory);

        return updateMenu;
    }

    public static Menu soldOut(Menu menu) {
        menu.setAvailable(false);
        return menu;
    }

    public static Menu restore(Menu menu) {
        menu.setAvailable(true);
        return menu;
    }
}
