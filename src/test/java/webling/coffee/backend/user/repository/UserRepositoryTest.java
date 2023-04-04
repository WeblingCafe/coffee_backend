package webling.coffee.backend.user.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.domain.user.repository.UserRepository;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userRepository_querydsl_기능_확인 () {
        String email = "joshua@snaps.com";
        String username = "조슈아";

        userRepository.save(new User(email, username));

        User user = userRepository.findByUsername(username);

        Assertions.assertThat(user.getEmail()).isEqualTo(email);
        Assertions.assertThat(user.getUsername()).isEqualTo(username);
    }
}
