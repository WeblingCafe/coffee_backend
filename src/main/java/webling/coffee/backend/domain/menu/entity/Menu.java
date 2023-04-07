package webling.coffee.backend.domain.menu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import webling.coffee.backend.domain.cart.entity.Cart;
import webling.coffee.backend.global.base.BaseTime;
import webling.coffee.backend.global.enums.MenuCategory;

import java.util.ArrayList;
import java.util.List;

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
    private MenuCategory menuCategory;

    @NotNull
    private Long price;
    private String menuPhotoUrl;
    private boolean hotAvailable;
    private boolean coldAvailable;
    private boolean isAvailable;

    @Builder.Default
    @OneToMany (mappedBy = "menu")
    private List<Cart> orderMenus = new ArrayList<>();

}
