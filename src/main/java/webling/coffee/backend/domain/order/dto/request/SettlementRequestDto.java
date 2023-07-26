package webling.coffee.backend.domain.order.dto.request;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class SettlementRequestDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    @Schema(name = "SettlementRequestSearch")
    public static class Search {

        private String username;
        private String userNickname;
        @JsonUnwrapped
        private BaseField baseField;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @Schema(name = "SettlementRequestBaseField")
    public static class BaseField {

        private static final int MAX_SIZE = 200;

        private Integer page = 1;
        private Integer size = 10;
        private LocalDateTime fromDate;
        private LocalDateTime toDate;

        public long getOffSet () {
            return (long) (max(1, page) -1) * min(size, MAX_SIZE);
        }
    }
}
