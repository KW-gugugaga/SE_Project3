package conpanda9.shop.repository;

import conpanda9.shop.DTO.MyInfoEditDTO;
import conpanda9.shop.DTO.QuestionDTO;
import conpanda9.shop.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Transactional
    public void editUser(Long id, MyInfoEditDTO editDTO) {
        User user = em.find(User.class, id);
        user.setLoginId(editDTO.getLoginId());
        user.setNickname(editDTO.getNickname());
        user.setEmail(editDTO.getEmail());
        user.setPhoneNumber(editDTO.getPhoneNumber());
    }

    @Transactional
    public void editPw(Long id, String newPw) {
        em.find(User.class, id).setLoginPw(newPw);
    }

    @Transactional
    public void saveSeller(Seller seller) {
        em.persist(seller);
    }

    public List<Question> findQuestionByUser(Long id) {
        //user id로 user가 남긴 문의사항들 보기
        return em.createQuery("select q from Question as q order by q.lastModifiedDate desc", Question.class)
                .getResultList().stream()
                .filter(q -> q.getUser().getId().equals(id)).collect(Collectors.toList());
    }

    @Transactional
    public void saveQuestion(Question question) {
        em.persist(question);
    }

    public Question findQuestion(Long id) {
        return em.find(Question.class, id);
    }

    @Transactional
    public void editQuestion(Long id, QuestionDTO questionDTO) {
        Question question = em.find(Question.class, id);
        question.setTitle(questionDTO.getTitle());
        question.setText(questionDTO.getText());
        question.setLastModifiedDate(LocalDateTime.now());
    }

    @Transactional
    public void deleteQuestion(Long id) {
        em.remove(em.find(Question.class, id));
    }
}
