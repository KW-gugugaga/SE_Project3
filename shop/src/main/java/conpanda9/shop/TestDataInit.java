package conpanda9.shop;

import conpanda9.shop.domain.User;
import conpanda9.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class TestDataInit {

    private final UserRepository userRepository;

    @PostConstruct
    public void dataInit() {

        User admin = new User("admin", "admin!", "admin", "admin@gmail.com", "010-1111-1111");
        userRepository.save(admin);
        User user = new User("test", "test!", "nickname", "email@gmail.com", "010-0000-0000");
        userRepository.save(user);
        log.info("test data init");
    }
}
