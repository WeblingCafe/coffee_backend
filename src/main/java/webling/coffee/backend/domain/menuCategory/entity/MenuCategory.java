package webling.coffee.backend.domain.menuCategory.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import webling.coffee.backend.domain.menu.entity.Menu;

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

    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Menu> menuList = new ArrayList<>();

}
