package webling.coffee.backend.domain.order.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class OrderRequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Create {

        private List<Long> menus = new ArrayList<>();

    }

}
