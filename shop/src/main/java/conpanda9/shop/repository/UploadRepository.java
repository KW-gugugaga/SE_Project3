package conpanda9.shop.repository;

import conpanda9.shop.domain.Gifticon;
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
public class UploadRepository {

    private final EntityManager em;

    @Transactional
    public void saveGifticon(Gifticon gifticon) {
        em.persist(gifticon);
    }
}
