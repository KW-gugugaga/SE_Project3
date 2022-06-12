package conpanda9.shop.repository;

import conpanda9.shop.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class AdminRepository {

    private final EntityManager em;


    public List<Question> findAllQuestion() {
        return em.createQuery("select q from Question as q", Question.class).getResultList();
    }

    public Question findQuestion(Long id) {
        return em.find(Question.class, id);
    }

    @Transactional
    public void saveNotice(Notice notice) {
        em.persist(notice);
    }

    public Notice findNotice(Long id) {
        return em.find(Notice.class, id);
    }

    public List<Notice> findAllImportantNotice() {
        return em.createQuery("select n from Notice as n " +
                        "where n.important = true " +
                        "order by n.uploadDate desc", Notice.class)
                .getResultList();
    }

    public List<Notice> findAllNormalNotice() {
        return em.createQuery("select n from Notice as n " +
                        "where n.important = false " +
                        "order by n.uploadDate desc", Notice.class)
                .getResultList();
    }

    @Transactional
    public void deleteNotice(Long id) {
        em.remove(findNotice(id));
    }

    public List<Report> findAllReport() {
        return em.createQuery("select r from Report as r", Report.class).getResultList();
    }


    @Transactional
    public void addNoticeCount(Long id) {
        Notice notice = em.find(Notice.class, id);
        Long count = notice.getCount();
        notice.setCount(++count);
    }


    @Transactional
    public void addAnswer(Long id, String answer) {
        em.find(Question.class, id).setAnswer(answer);
    }

    @Transactional
    public void editAnswer(Long id, String answer) {
        em.find(Question.class, id).setAnswer(answer);
    }

    public List<User> findAllUser(){
        return em.createQuery("select u from User as u", User.class).getResultList();
    }
    public User findUser(Long id) {
        User user = em.find(User.class, id);   // pk id에 따라 객치(Data) 찾기
        return user;
    }

    @Transactional
    public void deleteUser(Long id) {
        em.remove(findUser(id));
    }

    public List<Seller> findAllSeller() {
        return em.createQuery("select s from Seller as s", Seller.class).getResultList();
    }

    public Optional<Seller> findSellerByUserId(Long id) {
        return findAllSeller().stream().filter(s -> Objects.equals(s.getUser().getId(), id)).findAny();
    }

    public List<Shared> findSharedByUserId(Long id) {
        return em.createQuery("select s from Shared as s where s.user.id = :id", Shared.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Sold> findSoldByUserId(Long id) {
        return em.createQuery("select s from Sold as s where s.user.id = :id", Sold.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Report> findReportByUserId(Long id) {
        return em.createQuery("select r from Report as r where r.user.id = :id", Report.class)
                .setParameter("id", id)
                .getResultList();
    }

    public Report findReport(Long id) {
        return em.find(Report.class, id);
    }

    public List<Gifticon> findAllGifticons() {
        return em.createQuery("select g from Gifticon as g", Gifticon.class).getResultList();
    }

    public Gifticon findGifticon(Long id) {
        return em.find(Gifticon.class, id);
    }

    @Transactional
    public void deleteGifticon(Long id) {
        em.remove(em.find(Gifticon.class, id));
    }
}
