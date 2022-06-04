package conpanda9.shop.repository;

import conpanda9.shop.domain.Notice;
import conpanda9.shop.domain.Question;
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
public class QuestionRepository {

    private final EntityManager em;

    @Transactional
    public void saveQuestion(Question question) {
        em.persist(question);
    }
    public List<Question> findAllQuestion() {
        log.info("QuestionRepository findAllQuestion");
        return em.createQuery("select q from Question as q", Question.class).getResultList();
    }


}
