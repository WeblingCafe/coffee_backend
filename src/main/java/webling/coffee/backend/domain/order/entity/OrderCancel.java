package webling.coffee.backend.domain.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import webling.coffee.backend.domain.order.dto.request.OrderRequestDto;
import webling.coffee.backend.global.base.BaseTime;

@Getter
@Setter(AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@DynamicUpdate
@Table(name = "order_cancel_mst")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCancel extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_CANCEL_ID")
    private Long orderCancelId;

    @OneToOne (fetch = FetchType.LAZY)
    private Order order;

    private String cancelMessage;

    public static OrderCancel toEntity(final @NotNull Order order, final @NotNull OrderRequestDto.Cancel request) {
        return OrderCancel.builder()
                .order(order)
                .cancelMessage(request.getCancelMessage())
                .build();
    }
}
