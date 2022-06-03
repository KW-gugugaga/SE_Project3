package conpanda9.shop.repository;

import conpanda9.shop.domain.Notice;
import conpanda9.shop.domain.Question;
import conpanda9.shop.domain.Report;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class AdminRepository {

    private final EntityManager em;

    public List<Question> findAllQuestion() {
        return em.createQuery("select q from Question as q", Question.class).getResultList();
    }

    public List<Report> findAllReport() {
        return em.createQuery("select r from Report as r", Report.class).getResultList();
    }

    public List<Notice> findAllImportantNotice() {
        return em.createQuery("select n from Notice as n " +
                        "where n.important = true " +
                        "order by n.uploadDate desc", Notice.class)
                .getResultList();
    }
}
