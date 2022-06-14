package conpanda9.shop.controller;

import conpanda9.shop.DTO.UploadDTO;
import conpanda9.shop.domain.*;
import conpanda9.shop.domain.gifticoncomparator.*;
import conpanda9.shop.service.ItemService;
import conpanda9.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final UserService userService;

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
        /**
         * 기본은 최신순으로 정렬
         * 브랜드에 속하는 기프티콘들 중 판매중인것만
         * 강퇴 회원의 상품은 띄우지 않음
         */
        List<Gifticon> gifticons = itemService.findBrand(id).getGifticonList()
                .stream()
                .filter(g -> g.getState().equals(GifticonState.Selling) && g.getSeller().getUser() != null)
                .sorted(new GifticonDateComparator())
                .collect(Collectors.toList());
        model.addAttribute("brand", brand);
        model.addAttribute("gifticons", gifticons);
        return "items/brand";
    }

    @GetMapping("/brand/{brandId}/{sort}")
    public String getBrandSorted(@PathVariable("brandId") Long id, @PathVariable("sort") String sort, Model model) {
        log.info("brand id={}", id);
        log.info("sort={}", sort);
        List<Gifticon> gifticons = itemService.findBrand(id).getGifticonList()
                .stream().filter(g -> g.getState().equals(GifticonState.Selling))
                .collect(Collectors.toList());   // 브랜드에 속하는 기프티콘들 중 판매중인것만
        itemService.sortGifticons(sort, gifticons);   // 기준에 따라 정렬
        model.addAttribute("brand", itemService.findBrand(id));
        model.addAttribute("gifticons", gifticons);
        return "items/brand";
    }

    @GetMapping("/special")
    public String getSpecial(Model model) {
        List<Gifticon> specialGitficons = itemService.findAllGitfitonSelling()
                .stream().filter(g -> g.getDiscountRate() >= 30.0)
                .collect(Collectors.toList());   // 판매중인 모든 기프티콘 중 할인율 30 이상인 것
        specialGitficons.sort(new GifticonDateComparator());   // 기본은 최신순
        model.addAttribute("gifticons", specialGitficons);
        return "items/special";
    }

    @GetMapping("/special/{sort}")
    public String getSpecialSorted(@PathVariable String sort, Model model) {
        log.info("special sort={}", sort);
        List<Gifticon> specialGitficons = itemService.findAllGitfitonSelling()
                .stream().filter(g -> g.getDiscountRate() >= 30.0)
                .collect(Collectors.toList());   // 판매중인 모든 기프티콘 중 할인율 30 이상인 것
        itemService.sortGifticons(sort, specialGitficons);   // 정렬기준에 따라 분류
        model.addAttribute("gifticons", specialGitficons);
        return "items/special";
    }

    @GetMapping("/deadline")
    public String getDeadline(Model model) {
        List<Gifticon> deadlineGitficons = itemService.findAllGitfitonSelling()
                .stream().filter(g -> g.getExpireDate().isBefore(LocalDate.now().plusDays(8)))
                .collect(Collectors.toList());// 판매중인 기프티콘 중 마감기한이 일주일 남은 기프티콘
        model.addAttribute("gifticons", deadlineGitficons);
        return "items/deadline";
    }

    @GetMapping("/deadline/{sort}")
    public String getDeadlineSorted(@PathVariable String sort, Model model) {
        List<Gifticon> deadlineGitficons = itemService.findAllGitfitonSelling()
                .stream().filter(g -> g.getExpireDate().isBefore(LocalDate.now().plusDays(8)))
                .collect(Collectors.toList());// 판매중인 기프티콘 중 마감기한이 일주일 남은 기프티콘
        itemService.sortGifticons(sort, deadlineGitficons);   // 정렬기준에 따라 분류
        model.addAttribute("gifticons", deadlineGitficons);
        return "items/deadline";
    }

    @GetMapping("/share")
    public String getShare(Model model) {
        List<Share> shares = itemService.findAllShareSharing();   //현재 나눔중인 것들만 가져오기
        model.addAttribute("shares", shares);
        return "items/shares";
    }

    @GetMapping("/share/{sort}")
    public String getShareSorted(@PathVariable("sort") String sort, Model model) {
        List<Share> shares = itemService.findAllShareSharing();   //현재 나눔중인 것들만 가져오기
        itemService.sortShares(sort, shares);
        model.addAttribute("shares", shares);
        return "items/shares";
    }

    @GetMapping("/upload")
    public String upload(HttpServletRequest request, Model model) {

        Long id = (Long) request.getSession().getAttribute("user");

        Optional<Seller> storeOptional = userService.findStore(id);
        if(storeOptional.isPresent()) {   // 상점이 있을 경우 등록가능
            List<Category> categories = itemService.findAllCategory();
            model.addAttribute("uploadDTO", new UploadDTO());
            model.addAttribute("categories", categories);
            model.addAttribute("brands", new Brand());
            return "items/upload";
        } else {
            return "redirect:/item";
        }
    }

    @PostMapping("/SelectedCategory")
    @ResponseBody
    public List<String> selectedCategoryCheck(@RequestParam("cate_id") String cate_id) {
        System.out.println("ItemController.SelectedCategory");
        long id = Long.parseLong(cate_id);
        System.out.println(cate_id);
        Category category = itemService.findCategory(id);
        List<Brand> brands = category.getBrandList();
        List<String> brandNameOnly = new ArrayList<String>();
        for (Brand brand : brands) {
            System.out.println("brand = " + brand.getName());
            brandNameOnly.add(brand.getName());
        }
//        List<Brand> brand=itemService.findBrandbyCategory(category.getId());
//        System.out.println(bran                                                                                         d);
        return brandNameOnly;
    }

    @PostMapping("/upload")
    public String upload(HttpServletRequest request, @Validated @ModelAttribute("uploadDTO") UploadDTO uploadDTO, BindingResult bindingResult,Model model) {

        /**
         * @Validated, 검사 객체, BindingResult 세트로 같이 써야함
         * 검사 객체에 Validation annotation으로 달아놓은 것 검사
         * message에 등록한 문구 출력
         */

        System.out.println("itemController post upload");
        List<Category> categories = itemService.findAllCategory();
        if (bindingResult.hasErrors()) {   // 필드 에러
            model.addAttribute("categories", categories);
            return "items/upload";
        }
        long originalPrice = Long.parseLong(uploadDTO.getOriginalPrice());
        long sellingPrice = Long.parseLong(uploadDTO.getSellingPrice());

        if(originalPrice<sellingPrice) {
            bindingResult.addError(new ObjectError("uploadDTO", "원가보다 판매가가 높습니다."));
            model.addAttribute("categories", categories);
            return "items/upload";
        }

        Long id = (Long) request.getSession().getAttribute("user");
        User user = userService.findUser(id);
        Optional<Seller> storeOptional = userService.findStore(id);
        Seller seller = storeOptional.get();
        long categoryId = Long.parseLong(uploadDTO.getCategoryId());
        Category category = itemService.findCategory(categoryId);
        Brand brand = itemService.findBrandByName(uploadDTO.getBrandName());
        LocalDate expireDate = LocalDate.parse(uploadDTO.getExpireDate(), DateTimeFormatter.ISO_DATE);

        Gifticon newGifticon = new Gifticon(uploadDTO.getName(), seller, category, brand, null, uploadDTO.getDescription(),
                originalPrice, sellingPrice, expireDate, LocalDateTime.now(), LocalDateTime.now());

        itemService.saveGifticon(newGifticon);

        return "redirect:/items/item/"+newGifticon.getId();
    }

    @GetMapping("/item/{g_id}")
    public String eachItem(HttpServletRequest request, @PathVariable("g_id") long g_id, Model model) {

        Long id = (Long) request.getSession().getAttribute("user");
        System.out.println("id = " + id);
        Gifticon gifticon = itemService.findGifticon(g_id);

        Optional<Seller> storeOptional = userService.findStore(id);
        Seller seller = storeOptional.get();
        System.out.println("seller.getId() = " + seller.getUser().getId());


        if(seller.getUser().getId()==id) {   // 내가 상품 주인이다!
            model.addAttribute("gifticon", gifticon);
            System.out.println("내꺼다");
            return "items/myitem";
        } else {
            model.addAttribute("gifticon", gifticon);
            return "items/item";
        }
    }
}
