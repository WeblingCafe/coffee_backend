package webling.coffee.backend.domain.user.repository;

import jakarta.validation.constraints.NotBlank;

public interface QueryUserRepository {

    boolean checkUserByEmail(final @NotBlank String email);

}
