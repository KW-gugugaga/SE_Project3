package conpanda9.shop.repository;

import conpanda9.shop.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    @Transactional
    public void saveCategory(Category category) {
        em.persist(category);
    }
}
