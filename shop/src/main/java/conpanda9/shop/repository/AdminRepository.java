package conpanda9.shop.repository;

import conpanda9.shop.domain.Notice;
import conpanda9.shop.domain.Question;
import conpanda9.shop.domain.Report;
import conpanda9.shop.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

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

}
