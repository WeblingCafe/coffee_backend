package webling.coffee.backend.domain.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import webling.coffee.backend.global.base.BaseTime;

@Entity
public class Order extends BaseTime {

    @Id
    @GeneratedValue
    private Long id;


}
