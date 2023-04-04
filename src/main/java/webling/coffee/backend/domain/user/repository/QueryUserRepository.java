package webling.coffee.backend.domain.user.repository;

import webling.coffee.backend.domain.user.entity.User;

public interface QueryUserRepository {

    User findByUsername (String username);

    boolean checkDuplicationUser(String email);

}
