package conpanda9.shop.service;

import conpanda9.shop.domain.Brand;
import conpanda9.shop.domain.Category;
import conpanda9.shop.domain.Gifticon;
import conpanda9.shop.domain.datecomparator.*;
import conpanda9.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Category> findAllCategory() {
        return itemRepository.findAllCategory();
    }

    public Category findCategory(Long id) {
        return itemRepository.findCategory(id);
    }

    public Brand findBrand(Long id) {
        return itemRepository.findBrand(id);
    }

    public List<Gifticon> findAllGifticon() {
        return itemRepository.findAllGifticon();
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
}
