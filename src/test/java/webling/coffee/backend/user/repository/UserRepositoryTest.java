package webling.coffee.backend.user.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import webling.coffee.backend.domain.user.repository.UserRepository;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userRepository_querydsl_기능_확인 () {
        String email = "joshua@snaps.com";
        String username = "조슈아";

    }
}
