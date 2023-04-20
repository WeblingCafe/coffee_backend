package webling.coffee.backend.global.redis.entity;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;


@Getter
@Setter
@NoArgsConstructor
@RedisHash("CoffeeRefreshToken")
public class RefreshToken {
    @Id
    private String refreshTokenKey;

    @Column(nullable = false)
    private String refreshTokenValue;

    @TimeToLive
    private Integer expiration;

    public void updateValue(String token) {
        this.refreshTokenValue = token;
    }

    @Builder
    public RefreshToken(String key, String value, Integer expiration) {
        this.refreshTokenKey = key;
        this.refreshTokenValue = value;
        this.expiration = expiration;
    }
}
