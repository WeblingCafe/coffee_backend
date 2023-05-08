package webling.coffee.backend.domain.menu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.global.base.BaseTime;

@Getter
@Setter(AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@Table(name = "favorite_menu_mst")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FavoriteMenu extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAVORITE_MENU_ID")
    private Long favoriteMenuId;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "USER_ID")
    private User user;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    private boolean favorite;

    public static FavoriteMenu toEntity(final @NotNull User user, final @NotNull Menu menu) {
        return FavoriteMenu.builder()
                .user(user)
                .menu(menu)
                .favorite(true)
                .build();
    }

    public static FavoriteMenu updateFavorite(final FavoriteMenu favoriteMenu) {
        favoriteMenu.setFavorite(!favoriteMenu.isFavorite());
        return favoriteMenu;
    }
}
