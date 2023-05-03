package webling.coffee.backend.domain.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

public class SettlementRequestDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    @Schema(name = "SettlementRequestSearch")
    public static class Search {

        private String username;
        private String userNickname;
        private RegDate regDate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @Schema(name = "SettlementRequestRegDate")
    public static class RegDate {
        private LocalDateTime fromDate;
        private LocalDateTime toDate;
    }
}
