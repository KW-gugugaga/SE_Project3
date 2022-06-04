package conpanda9.shop.service;

import conpanda9.shop.domain.Brand;
import conpanda9.shop.domain.Category;
import conpanda9.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
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
}
