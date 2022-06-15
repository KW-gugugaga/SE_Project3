package conpanda9.shop.service;

import conpanda9.shop.DTO.SearchDTO;
import conpanda9.shop.domain.*;
import conpanda9.shop.domain.gifticoncomparator.*;
import conpanda9.shop.domain.sharecomparator.ShareDateComparator;
import conpanda9.shop.domain.sharecomparator.ShareExpiredDateComparator;
import conpanda9.shop.domain.sharecomparator.ShareNameComparator;
import conpanda9.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * category
     */
    public List<Category> findAllCategory() {
        return itemRepository.findAllCategory();
    }

    public Category findCategory(Long id) {
        return itemRepository.findCategory(id);
    }

    /**
     * brand
     */
    public Brand findBrand(Long id) {
        return itemRepository.findBrand(id);
    }
    public Brand findBrandByName(String name){
        return itemRepository.findBrandByName(name);
    }
    /**
     * gifticon
     */
    public List<Gifticon> findAllGifticon() {
        return itemRepository.findAllGifticon();
    }

    public List<Gifticon> findAllGitfitonSelling() {
        return itemRepository.findAllGifticonSelling();
    }

    public List<Gifticon> findAllGitficonSold() {
        return itemRepository.findAllGifticonSelling();
    }

    public void sortGifticons(String sort, List<Gifticon> gifticons) {
        if(sort.equals("latest")) {
            gifticons.sort(new GifticonDateComparator());   // 최신순으로 정렬
        } else if(sort.equals("price-asc")) {
            gifticons.sort(new GifticonPriceComparator());   // 가격 오름차순 정렬
        } else if(sort.equals("price-desc")) {
            gifticons.sort(new GifticonPriceComparator().reversed());
        } else if(sort.equals("discount-desc")) {
            gifticons.sort(new GifticonDiscountComparator());
        } else if(sort.equals("discount-asc")) {
            gifticons.sort(new GifticonDiscountComparator().reversed());
        } else if(sort.equals("name")) {
            gifticons.sort(new GitficonNameComparator());
        } else if(sort.equals("deadline")) {
            gifticons.sort(new GifticonExpiredDateComparator());
        }
    }

    public void saveGifticon(Gifticon gifticon) {
        itemRepository.saveGifticon(gifticon);
    }

    public Gifticon findGifticon(Long id) {
        return itemRepository.findGifticon(id);
    }

    public void setImagePath(Gifticon gifticon,String fakePath, String realPath){
        itemRepository.setImagePath(gifticon,fakePath,realPath);
    }

    public void setNullGifticon(Long id) {
        Gifticon gifticon = itemRepository.findGifticon(id);
        gifticon.getSeller().getGifticonList().remove(gifticon);   // seller 리스트에서 기프티콘 목록 삭제
        gifticon.getBrand().getGifticonList().remove(gifticon);   // brand 리스트에서 기프티콘 목록 삭제
        gifticon.getCategory().getGitficonList().remove(gifticon);   // category 리스트에서 기프티콘 목록 삭제
        itemRepository.deleteGifticon(id);
    }

    /**
     * share
     */
    public List<Share> findAllShare() {
        return itemRepository.findAllShare();
    }

    public List<Share> findAllShareSharing() {
        return itemRepository.findAllShareSharing();
    }

    public List<Share> findAllShareShared() {
        return itemRepository.findAllShareShared();
    }

    public void sortShares(String sort, List<Share> shares) {
        if(sort.equals("latest")) {
            shares.sort(new ShareDateComparator());   // 최신순으로 정렬
        } else if(sort.equals("name")) {
            shares.sort(new ShareNameComparator());   // 이름순 정렬
        } else if(sort.equals("deadline")) {
            shares.sort(new ShareExpiredDateComparator());   // 마감기한 순 정렬
        }
    }

    public List<Gifticon> searchByBrand(String str){
        return itemRepository.searchByBrand(str);
    }
    public List<Gifticon> searchByCategory(String str){
        return itemRepository.searchByCategory(str);
    }
    public List<Gifticon> searchByItem(String str){
        return itemRepository.searchByItem(str);
    }
    public Optional<List<Gifticon>> search(SearchDTO searchDTO) {
        Optional<List<Gifticon>> oneByBrand = Optional.ofNullable(itemRepository.searchByBrand(searchDTO.getSearchWhat()));
        if(oneByBrand.isEmpty()) {   // 일치 브랜드 없을 경우
            return null;
        }
        else {
            return oneByBrand;
        }
    }

    public void saveSold(Sold sold) {
        itemRepository.saveSold(sold);
    }

    @Transactional
    public void updateModifiedDate(Gifticon gifticon) {
        gifticon.setLastModifiedDate(LocalDateTime.now());
    }

    public void updateGifticonStateSold(Gifticon gifticon) {
        itemRepository.updateGifticonStateSold(gifticon);
    }
}
