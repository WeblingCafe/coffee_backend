package webling.coffee.backend.domain.order.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import webling.coffee.backend.domain.menu.entity.Menu;
import webling.coffee.backend.global.base.BaseTime;

@Getter
@Setter(AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderMenu extends BaseTime {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long orderMenuId;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

}
