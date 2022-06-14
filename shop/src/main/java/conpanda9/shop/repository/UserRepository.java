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

    public List<Seller> findAllStore() {
        return em.createQuery("select s from Seller as s", Seller.class).getResultList();
    }


    public Optional<Seller> findStore(Long id) {
        return findAllStore().stream().filter(s -> s.getUser().getId().equals(id)).findAny();
    }

    public Optional<Seller> findOtherStore(Long id) {
        return findAllStore().stream().filter(s -> s.getId().equals(id)).findAny();
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
    public void saveStore(Seller store) {
        em.persist(store);
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


    public List<Sold> findSolds(Long id) {
        return em.createQuery("select s from Sold as s where s.seller.id = :id", Sold.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Gifticon> findSellings(Long id) {
        return em.createQuery("select g from Gifticon as g where g.seller.id = :id and g.state = conpanda9.shop.domain.GifticonState.Selling", Gifticon.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Sold> findBuys(Long id) {
        return em.createQuery("select s from Sold as s", Sold.class).getResultList()
                .stream().filter(s -> s.getUser().getId().equals(id)).collect(Collectors.toList());
    }

    public List<Review> findAllReview() {
        return em.createQuery("select r from Review as r", Review.class).getResultList();
    }

    public List<Review> findReviews(Long id) {
        return findAllReview().stream().filter(r -> r.getSeller().getId().equals(id))
                .collect(Collectors.toList());
    }

    public Optional<User> findId(String nickname, String email) {
        return findAll().stream()
                .filter(u -> u.getNickname().equals(nickname) && u.getEmail().equals(email))
                .findAny();
    }

    public Optional<User> findPw(String loginId, String email) {
        return findAll().stream()
                .filter(u -> u.getLoginId().equals(loginId) && u.getEmail().equals(email))
                .findAny();
    }

    @Transactional
    public void saveReport(Report report) {
        em.persist(report);
    }

    public Long countAlarm(Long id){
        return (Long) em.createQuery("select count(a) from Alarm as a where a.user.id = :id and a.checked = false")
                .setParameter("id",id)
                .getSingleResult();
    }
    public List<Alarm> findAlarm(Long id) { //유저의 정보 받아와서 알람 찾아옴
        return em.createQuery("select a from Alarm as a order by a.alarmDate desc", Alarm.class)
                .getResultList().stream()
                .filter(a -> a.getUser().getId().equals(id)).collect(Collectors.toList());
    }
    @Transactional
    public void saveAlarm(Alarm Alarm) {
        em.persist(Alarm);
    }

    public Alarm findOneAlarm(Long id){
        return em.find(Alarm.class,id);
    }

    @Transactional
    public void saveReview(Review userReview) {
        em.persist(userReview);
    }

    public Optional<User> findOneByPhoneNumber(String phoneNumber) {
        log.info("UserRepository findOneByPhoneNumber");
        return findAll().stream()
                .filter(m ->m.getPhoneNumber().equals(phoneNumber))
                .findAny();
    }

    public List<Sold> findAllSold() {
        return em.createQuery("select s from Sold as s", Sold.class).getResultList();
    }
}
