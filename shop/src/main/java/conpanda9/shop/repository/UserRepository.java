package conpanda9.shop.repository;

import conpanda9.shop.domain.Review;
import conpanda9.shop.domain.Sold;
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

    @Transactional   // DB 저장은 할 때마다 commit 해야돼서 이거 필수로 붙여야함!
    public void save(User user) {
        em.persist(user);   // DB 저장 함수
    }

    public User find(Long id) {
        User user = em.find(User.class, id);   // pk id에 따라 객치(Data) 찾기
        return user;
    }

    public User findSeller(Long id) {
        Sold sold = em.find(Sold.class, id);   // 거래 내역 찾고
        User user = sold.getUser();   // 해당 거래내역에 연결된 판매자(user) 객체 가져오기
        return user;
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
