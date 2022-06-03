package conpanda9.shop.repository;

import conpanda9.shop.domain.Notice;
import conpanda9.shop.domain.Question;
import conpanda9.shop.domain.Report;
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

    public List<Question> findAllQuestion() {
        return em.createQuery("select q from Question as q", Question.class).getResultList();
    }

    public List<Report> findAllReport() {
        return em.createQuery("select r from Report as r", Report.class).getResultList();
    }


}
