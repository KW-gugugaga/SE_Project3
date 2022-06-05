package conpanda9.shop.repository;

import conpanda9.shop.domain.Brand;
import conpanda9.shop.domain.Category;
import conpanda9.shop.domain.Gifticon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    @Transactional
    public void saveCategory(Category category) {
        em.persist(category);
    }

    public List<Category> findAllCategory() {
        return em.createQuery("select c from Category as c", Category.class).getResultList();
    }

    public Category findCategory(Long id) {
        return em.find(Category.class, id);
    }

    @Transactional
    public void saveBrand(Brand brand) {
        em.persist(brand);
    }

    public Brand findBrand(Long id) {
        return em.find(Brand.class, id);
    }

    @Transactional
    public void saveGifticon(Gifticon gifticon) {
        em.persist(gifticon);
    }

    public List<Gifticon> findAllGifticon() {
        return em.createQuery("select g from Gifticon as g", Gifticon.class).getResultList();
    }
}
