package conpanda9.shop;

import conpanda9.shop.domain.Notice;
import conpanda9.shop.domain.User;
import conpanda9.shop.repository.AdminRepository;
import conpanda9.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class TestDataInit {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    //@PostConstruct
    public void dataInit() {

        User admin = new User("admin", "admin!", "admin", "admin@gmail.com", "010-1111-1111");
        userRepository.save(admin);
        User user = new User("test", "test!", "nickname", "email@gmail.com", "010-0000-0000");
        userRepository.save(user);
        log.info("test data init");

        Notice notice1 = new Notice("notice1", "contents1", LocalDateTime.now(), LocalDateTime.now(), false);
        Notice notice2 = new Notice("notice2", "contents2", LocalDateTime.now(), LocalDateTime.now(), true);
        adminRepository.saveNotice(notice1);
        adminRepository.saveNotice(notice2);
    }
}
