package conpanda9.shop.controller;

import conpanda9.shop.DTO.MyInfoEditDTO;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    public String getBrand(@PathVariable("brandId") Long id, Model model,
                           @ModelAttribute("upSuccess") String upSuccess) {
        log.info("brand id={}", id);
        Brand brand = itemService.findBrand(id);
        /**
         * ????????? ??????????????? ??????
         * ???????????? ????????? ??????????????? ??? ??????????????????
         * ?????? ????????? ????????? ????????? ??????
         */
        List<Gifticon> gifticons = itemService.findBrand(id).getGifticonList()
                .stream()
                .filter(g -> g.getState().equals(GifticonState.Selling) && g.getSeller().getUser() != null)
                .sorted(new GifticonDateComparator())
                .collect(Collectors.toList());
        model.addAttribute("brand", brand);
        model.addAttribute("gifticons", gifticons);
        model.addAttribute("upSuccess", upSuccess);
        return "items/brand";
    }

    @GetMapping("/brand/{brandId}/{sort}")
    public String getBrandSorted(@PathVariable("brandId") Long id, @PathVariable("sort") String sort, Model model) {
        log.info("brand id={}", id);
        log.info("sort={}", sort);
        List<Gifticon> gifticons = itemService.findBrand(id).getGifticonList()
                .stream().filter(g -> g.getState().equals(GifticonState.Selling))
                .collect(Collectors.toList());   // ???????????? ????????? ??????????????? ??? ??????????????????
        itemService.sortGifticons(sort, gifticons);   // ????????? ?????? ??????
        model.addAttribute("brand", itemService.findBrand(id));
        model.addAttribute("gifticons", gifticons);
        return "items/brand";
    }

    @GetMapping("/special")
    public String getSpecial(Model model) {
        List<Gifticon> specialGitficons = itemService.findAllGitfitonSelling()
                .stream().filter(g -> g.getDiscountRate() >= 50.0)
                .collect(Collectors.toList());   // ???????????? ?????? ???????????? ??? ????????? 30 ????????? ???
        specialGitficons.sort(new GifticonDateComparator());   // ????????? ?????????
        model.addAttribute("gifticons", specialGitficons);
        return "items/special";
    }

    @GetMapping("/special/{sort}")
    public String getSpecialSorted(@PathVariable String sort, Model model) {
        log.info("special sort={}", sort);
        List<Gifticon> specialGitficons = itemService.findAllGitfitonSelling()
                .stream().filter(g -> g.getDiscountRate() >= 30.0)
                .collect(Collectors.toList());   // ???????????? ?????? ???????????? ??? ????????? 30 ????????? ???
        itemService.sortGifticons(sort, specialGitficons);   // ??????????????? ?????? ??????
        model.addAttribute("gifticons", specialGitficons);
        return "items/special";
    }

    @GetMapping("/deadline")
    public String getDeadline(Model model) {
        List<Gifticon> deadlineGitficons = itemService.findAllGitfitonSelling()
                .stream().filter(g -> g.getExpireDate().isBefore(LocalDate.now().plusDays(8)))
                .collect(Collectors.toList());// ???????????? ???????????? ??? ??????????????? ????????? ?????? ????????????
        model.addAttribute("gifticons", deadlineGitficons);
        return "items/deadline";
    }

    @GetMapping("/deadline/{sort}")
    public String getDeadlineSorted(@PathVariable String sort, Model model) {
        List<Gifticon> deadlineGitficons = itemService.findAllGitfitonSelling()
                .stream().filter(g -> g.getExpireDate().isBefore(LocalDate.now().plusDays(8)))
                .collect(Collectors.toList());// ???????????? ???????????? ??? ??????????????? ????????? ?????? ????????????
        itemService.sortGifticons(sort, deadlineGitficons);   // ??????????????? ?????? ??????
        model.addAttribute("gifticons", deadlineGitficons);
        return "items/deadline";
    }

    @GetMapping("/share")
    public String getShare(Model model) {
        List<Share> shares = itemService.findAllShareSharing();   //?????? ???????????? ????????? ????????????
        model.addAttribute("shares", shares);
        return "items/shares";
    }

    @GetMapping("/share/{sort}")
    public String getShareSorted(@PathVariable("sort") String sort, Model model) {
        List<Share> shares = itemService.findAllShareSharing();   //?????? ???????????? ????????? ????????????
        itemService.sortShares(sort, shares);
        model.addAttribute("shares", shares);
        return "items/shares";
    }

    @GetMapping("/upload")
    public String upload(HttpServletRequest request, Model model) {

        Long id = (Long) request.getSession().getAttribute("user");

        Optional<Seller> storeOptional = userService.findStore(id);
        if(storeOptional.isPresent()) {   // ????????? ?????? ?????? ????????????
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
    public String upload(HttpServletRequest request, @Validated @ModelAttribute("uploadDTO") UploadDTO uploadDTO,
                         BindingResult bindingResult, Model model, @RequestPart(value="fakeFile",required = false)  MultipartFile fakeFile,@RequestPart(value="realFile",required = false)  MultipartFile realFile) throws IOException {

        /**
         * @Validated, ?????? ??????, BindingResult ????????? ?????? ?????????
         * ?????? ????????? Validation annotation?????? ???????????? ??? ??????
         * message??? ????????? ?????? ??????
         */

        System.out.println("itemController post upload");
        System.out.println("fakeFile = " + fakeFile.getContentType());

        List<Category> categories = itemService.findAllCategory();
        if (bindingResult.hasErrors()) {   // ?????? ??????
            model.addAttribute("categories", categories);
            return "items/upload";
        }
        long originalPrice = Long.parseLong(uploadDTO.getOriginalPrice());
        long sellingPrice = Long.parseLong(uploadDTO.getSellingPrice());

        if(originalPrice<sellingPrice) {
            bindingResult.addError(new ObjectError("uploadDTO", "???????????? ???????????? ????????????."));
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

        Gifticon newGifticon = new Gifticon(uploadDTO.getName(), seller, category, brand, null, null, uploadDTO.getDescription(),
                originalPrice, sellingPrice, expireDate, LocalDateTime.now(), LocalDateTime.now());
        
        itemService.saveGifticon(newGifticon);
        String[] fakeType = fakeFile.getContentType().split("/");
        String[] realType = realFile.getContentType().split("/");
        String fakePath = saveImage(newGifticon,fakeFile,"fake."+fakeType[1]);
        String realPath = saveImage(newGifticon,realFile,"real."+realType[1]);

        itemService.setImagePath(newGifticon,fakePath,realPath);
        log.info("new id={}", newGifticon.getId());

        return "redirect:/items/item/"+newGifticon.getId();
    }

    @GetMapping("/item/{g_id}")
    public String eachItem(HttpServletRequest request, @PathVariable("g_id") long g_id, Model model) {
        Long id = (Long) request.getSession().getAttribute("user");
        System.out.println("id = " + id);
        Gifticon gifticon = itemService.findGifticon(g_id);
        Optional<Seller> storeOptional = userService.findStore(id);
        if(storeOptional.isPresent()) {   // ?????? ????????? ?????? ???
            if(storeOptional.get().getUser().getId().equals(id)) {   // ?????? ?????? ????????????!
                model.addAttribute("gifticon", gifticon);
                System.out.println("?????????");
                return "items/myitem";
            } else {   // ?????? ????????? ?????? ?????????
                return "items/item";
            }
        } else {   // ?????? ????????? ?????? -> ?????? ??????
            model.addAttribute("gifticon", gifticon);
            return "items/item";
        }
    }

    public String saveImage(Gifticon gifticon, MultipartFile imgFile, String what) throws IOException {

        String oriImgName = imgFile.getOriginalFilename();
        String imgName = "";

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/img/gifticon";

        long gid = gifticon.getId();

        String savedFileName = gid + "_" + what; // ????????? -> imgName

        imgName = savedFileName;

        File saveFile = new File(projectPath, imgName);

        imgFile.transferTo(saveFile);

        return "/img/gifticon/"+imgName;
    }

    @GetMapping("/item/purchase/{gifticonId}")
    public String getItemPurchase(@PathVariable("gifticonId") Long gId,
                                  HttpServletRequest request, Model model) {
        Long userId = (Long) request.getSession().getAttribute("user");
        String error = null;
        Long restPoint = null;
        if(userId == null) {
            return "redirect:/";
        }
        User user = userService.findUser(userId);
        Long point = user.getPoint();   // ????????? ?????? ?????????
        Gifticon gifticon = itemService.findGifticon(gId);

         model.addAttribute("point", point);
         model.addAttribute("gifticon", gifticon);
         if(point < gifticon.getSellingPrice()) {   // ?????? ????????? ??????
             error = "?????? ???????????? ???????????????. ?????? ????????? ???????????????.";
             model.addAttribute("error", error);
             model.addAttribute("restPoint", restPoint);
         } else {
             restPoint = point - gifticon.getSellingPrice();
             model.addAttribute("restPoint", restPoint);
             model.addAttribute("error", null);
         }
        return "items/purchase";
    }

    @PostMapping("/item/purchase/{gifticonId}")
    public String postItemPurchase(@PathVariable("gifticonId") Long gId, RedirectAttributes rttr,
                                   HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("user");
        User user = userService.findUser(userId);
        Gifticon gifticon = itemService.findGifticon(gId);
        itemService.updateGifticonStateSold(gifticon);   // gifticon state
        Sold sold = new Sold(gifticon, gifticon.getSeller(), user, gifticon.getSellingPrice(), LocalDateTime.now());
        itemService.saveSold(sold);   // ?????? ?????? ??????
        userService.minusPoint(user, gifticon.getSellingPrice());   // ????????? ????????? ??????
        String message = "???????????? ?????? " + gifticon.getName() + "???(???) ?????? ?????????????????????.";
        Alarm alarm = new Alarm(gifticon.getSeller().getUser(), LocalDateTime.now(), "?????? ?????? ?????? ??????", message, false);
        userService.saveAlarm(alarm);   // ??????????????? ?????? ??????
        userService.plusPoint(gifticon.getSeller().getUser(), gifticon.getSellingPrice());   // ????????? ????????? ??????
        rttr.addFlashAttribute("purSuccess", "true");
        return "redirect:/user/store/buy";
    }

    @GetMapping("/item/modify/{itemId}")
    public String getModifyItem(HttpServletRequest request, @PathVariable("itemId") Long id, Model model) {
        Long u_id = (Long) request.getSession().getAttribute("user");
        List<Category> categories = itemService.findAllCategory();
        Gifticon gifticon = itemService.findGifticon(id);
        model.addAttribute("uploadDTO", new UploadDTO());
        model.addAttribute("categories", categories);
        model.addAttribute("gifticon", gifticon);
        return "items/modify";
    }
    @PostMapping("/item/modify/{itemId}")
    public String postModifyItem(HttpServletRequest request,@PathVariable("itemId") Long id,@Validated @ModelAttribute("uploadDTO") UploadDTO uploadDTO,BindingResult bindingResult, Model model,
                                 @RequestParam("newName") String newName,
                                 @RequestParam("newOriginalPrice") String newOriginalPrice,
                                 @RequestParam("newSellingPrice") String newSellingPrice,
                                 @RequestParam("newDescription") String newDescription,
                                 @RequestPart(value="fakeFile",required = false)  MultipartFile fakeFile,@RequestPart(value="realFile",required = false)  MultipartFile realFile) throws IOException {

        Gifticon gifticon = itemService.findGifticon(id);
        List<Category> categories = itemService.findAllCategory();

        Long u_id = (Long) request.getSession().getAttribute("user");
        User user = userService.findUser(u_id);
        Optional<Seller> storeOptional = userService.findStore(u_id);
        Seller seller = storeOptional.get();

        long originalPrice = Long.parseLong(newOriginalPrice);
        long sellingPrice = Long.parseLong(newSellingPrice);

        long categoryId = Long.parseLong(uploadDTO.getCategoryId());
        Category category = itemService.findCategory(categoryId);
        Brand brand = itemService.findBrandByName(uploadDTO.getBrandName());
        LocalDate expireDate = LocalDate.parse(uploadDTO.getExpireDate(), DateTimeFormatter.ISO_DATE);

        System.out.println("newExpireDate = " + expireDate);

        String[] fakeType = fakeFile.getContentType().split("/");
        String[] realType = realFile.getContentType().split("/");
        String fakePath = saveImage(gifticon,fakeFile,"fake."+fakeType[1]);
        String realPath = saveImage(gifticon,realFile,"real."+realType[1]);

        itemService.setImagePath(gifticon,fakePath,realPath);
        itemService.modifyGifticon(gifticon,newName,category,brand,originalPrice,sellingPrice,expireDate,newDescription);

        return "redirect:/items/item/"+gifticon.getId();
    }

    @GetMapping("/item/delete/{itemId}")
    public String getDeleteItem(@PathVariable("itemId") Long id) {
        log.info("gifticon id={}", id);
        itemService.setNullGifticon(id);
        return "redirect:/user/store/selling";
    }

    @GetMapping("/item/up/{itemId}")
    public String getItemUp(@PathVariable("itemId") Long id, HttpServletResponse response,
                            RedirectAttributes rttr) throws IOException {
        Gifticon gifticon = itemService.findGifticon(id);
        itemService.updateModifiedDate(gifticon);
        response.setContentType("text/html;charset=euc-kr");
        PrintWriter writer = response.getWriter();
        rttr.addFlashAttribute("upSuccess", "true");
        return "redirect:/items/brand/" + gifticon.getBrand().getId();
    }
}
