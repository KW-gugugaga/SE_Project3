package conpanda9.shop.repository;

import conpanda9.shop.domain.Review;
import conpanda9.shop.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepository {

    private final EntityManager em;

    @Transactional
    public void save(User user) {
        em.persist(user);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User as u", User.class).getResultList();
    }

    public Optional<User> findOneById(String loginId) {
        log.info("UserRepository findOneById");
        return findAll().stream()
                .filter(m ->m.getLoginId().equals(loginId))
                .findAny();
    }

    public Optional<User> findOneByNickname(String nickname) {
        log.info("UserRepository findOneByNickname");
        return findAll().stream()
                .filter(m ->m.getNickname().equals(nickname))
                .findAny();
    }

    public Optional<User> findOneByEmail(String email) {
        log.info("UserRepository findOneByEmail");
        return findAll().stream()
                .filter(m ->m.getEmail().equals(email))
                .findAny();
    }
}
