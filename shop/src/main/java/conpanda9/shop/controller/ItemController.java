package conpanda9.shop.controller;

import conpanda9.shop.domain.Brand;
import conpanda9.shop.domain.Category;
import conpanda9.shop.domain.Gifticon;
import conpanda9.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/category/{categoryId}")
    public String getCategory(@PathVariable("categoryId") Long id, Model model) {
        log.info("category id={}", id);
        Category category = itemService.findCategory(id);
        List<Brand> brands = category.getBrandList();
        model.addAttribute("category", category);
        model.addAttribute("brands", brands);
        return "items/category";
    }

    @GetMapping("/brand/{brandId}")
    public String getBrand(@PathVariable("brandId") Long id, Model model) {
        log.info("brand id={}", id);
        Brand brand = itemService.findBrand(id);
        List<Gifticon> gifticons = brand.getGifticonList();
        model.addAttribute("brand", brand);
        model.addAttribute("gifticons", gifticons);
        return "items/brand";
    }


}
