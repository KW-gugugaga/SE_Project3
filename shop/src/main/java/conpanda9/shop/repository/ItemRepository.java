package conpanda9.shop.repository;

import conpanda9.shop.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ItemRepository {

    private final EntityManager em;

    /**
     * category
     */
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

    /**
     * brand
     */
    @Transactional
    public void saveBrand(Brand brand) {
        em.persist(brand);
    }

    public Brand findBrand(Long id) {
        return em.find(Brand.class, id);
    }

    public List<Brand> findAllBrand() {
        return em.createQuery("select b from Brand as b", Brand.class).getResultList();
    }

    public Brand findBrandByName(String name){
        return em.createQuery("select b from Brand as b where b.name = :searchName", Brand.class)
                .setParameter("searchName", name).getSingleResult();
    }

    /**
     * gifticon
     */
    @Transactional
    public void saveGifticon(Gifticon gifticon) {
        em.persist(gifticon);
    }

    public List<Gifticon> findAllGifticon() {
        return em.createQuery("select g from Gifticon as g", Gifticon.class).getResultList();
    }

    public List<Gifticon> findAllGifticonSelling() {
        return findAllGifticon().stream().filter(g -> g.getState().equals(GifticonState.Selling)).collect(Collectors.toList());
    }

    public List<Gifticon> findAllGifticonSold() {
        return findAllGifticon().stream().filter(g -> g.getState().equals(GifticonState.Selling)).collect(Collectors.toList());
    }

    public Gifticon findGifticon(Long id) {
        return em.find(Gifticon.class, id);
    }

    @Transactional
    public void updateGifticonStateSold(Gifticon gifticon) {
        gifticon.setState(GifticonState.Sold);
    }

    @Transactional
    public void updateGitficonStateSelling(Gifticon gifticon) {
        gifticon.setState(GifticonState.Selling);
    }

    @Transactional
    public void setImagePath(Gifticon gifticon,String fakePath, String realPath)
    {
        gifticon.setFakeImage(fakePath);
        gifticon.setRealImage(realPath);
    }

    @Transactional
    public void modifyGifticon(Gifticon gifticon,String name, Category category, Brand brand, Long originalPrice, Long sellingPrice, LocalDate expireDate,String description)
    {
        gifticon.setName(name);
        gifticon.setCategory(category);
        gifticon.setBrand(brand);
        gifticon.setOriginalPrice(originalPrice);
        gifticon.setSellingPrice(sellingPrice);
        gifticon.setExpireDate(expireDate);
        gifticon.setDescription(description);

        double discountRate=Math.round((originalPrice.doubleValue() - sellingPrice.doubleValue()) / originalPrice.doubleValue() * 1000) / 10.0;
        gifticon.setDiscountRate(discountRate);
        gifticon.setLastModifiedDate(LocalDateTime.now());
    }

    /**
     * share
     */
    @Transactional
    public void saveShare(Share share) {
        em.persist(share);
    }

    public List<Share> findAllShare() {
        return em.createQuery("select s from Share as s", Share.class).getResultList();
    }

    public List<Share> findAllShareSharing() {
        List<Share> collect = findAllShare().stream().filter(s -> s.getState().equals(ShareState.Sharing)).collect(Collectors.toList());
        for (Share share : collect) {
            log.info("share name={}", share.getId());
        }
        return collect;
    }

    public List<Share> findAllShareShared() {
        return findAllShare().stream().filter(s -> s.getState().equals(ShareState.Shared)).collect(Collectors.toList());
    }

    @Transactional
    public void updateShareStateShared(Share share) {
        share.setState(ShareState.Shared);
    }

    @Transactional
    public void updateShareStateSharing(Share share) {
        share.setState(ShareState.Sharing);
    }

    /**
     * shared(?????? ?????? ?????? ?????????)
     */
    @Transactional
    public void saveShared(Shared shared) {
        em.persist(shared);
    }

    @Transactional
    public void saveQuestion(Question question) {
        em.persist(question);
    }

    public List<Gifticon> searchByBrand(String str) {
        return em.createQuery("select g from Gifticon as g where g.brand.name = :searchWhat", Gifticon.class)
                .setParameter("searchWhat", str)
                .getResultList();
    }
    public List<Gifticon> searchByCategory(String str) {
        return em.createQuery("select g from Gifticon as g where g.category.name = :searchWhat", Gifticon.class)
                .setParameter("searchWhat", str)
                .getResultList();
    }
    public List<Gifticon> searchByItem(String str) {
        return em.createQuery("select g from Gifticon as g where g.name = :searchWhat", Gifticon.class)
                .setParameter("searchWhat", str)
                .getResultList();
    }

    /**
     * sold(?????? ??????)
     */
    @Transactional
    public void saveSold (Sold sold){
        em.persist(sold);
    }

    @Transactional
    public void saveReview(Review review) {
        em.persist(review);
    }

    @Transactional
    public void deleteGifticon(Long id) {
        em.remove(em.find(Gifticon.class, id));
    }
}
