package conpanda9.shop.controller;

import conpanda9.shop.domain.*;
import conpanda9.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/store")
@Slf4j
@RequiredArgsConstructor
public class StoreController {

    private final UserService userService;

    @GetMapping("/info/{storeId}")
    public String getStoreInfo(@PathVariable("storeId") Long id,
                               @ModelAttribute("reportSuccess") String reportSuccess,
                               Model model) {
        /**
         * 다른 사람의 상점 찾기
         */
        List<Gifticon> sellings = userService.findSellings(id);
        Optional<Seller> store = userService.findOtherStore(id);
        Double starRate = Math.round(userService.getStoreStarRate(id) * 10) / 10.0;

        model.addAttribute("store", store.get());
        model.addAttribute("name", store.get().getName());
        model.addAttribute("starRate", starRate);
        model.addAttribute("reportSuccess", reportSuccess);
        model.addAttribute("sellings", sellings);   // 판매중 내역

        return "user/otherstore/info";
    }

    @GetMapping("/report/{storeId}")
    public String getStoreReport(@PathVariable Long storeId, Model model) {
        model.addAttribute("error", null);
        return "user/otherstore/report";
    }

    @PostMapping("/report/{storeId}")
    public String postStoreReport(HttpServletRequest request, RedirectAttributes rttr,
                                  @PathVariable Long storeId,
                                  @RequestParam("reportReason") String reportReason,
                                  @RequestParam(value = "reason", defaultValue = "") String extraReason,
                                  @RequestParam(value = "gifticon", defaultValue = "") String gifticon,
                                  Model model) {
        String error = null;
        if (reportReason.equals("default")) {
            error = "신고 사유를 선택해주세요.";
            model.addAttribute("error", error);
            return "user/otherstore/report";
        } else if (reportReason.equals("extra")) {
            if (extraReason.equals("")) {
                error = "기타 신고 사유를 입력해주세요.";
                model.addAttribute("error", error);
                return "user/otherstore/report";
            }
        } else {
            if (gifticon.equals("")) {
                error = "신고하고자 하는 상품명을 입력해주세요.";
                model.addAttribute("error", error);
                return "user/otherstore/report";
            }
        }

        // TODO
        // 신고 내역 등록
        Long userId = (Long) request.getSession().getAttribute("user");
        if (userId == null) {
            return "redirect:/";
        }
        User user = userService.findUser(userId);
        ReportReason reasonEnum = null;
        String content = null;
        if (reportReason.equals("extra")) {
            reasonEnum = ReportReason.EXTRA;
            content = extraReason;
        } else {
            content = gifticon;
            if (reportReason.equals("duplicate")) {
                reasonEnum = ReportReason.DUPLICATE;
            } else if (reportReason.equals("fake")) {
                reasonEnum = ReportReason.FAKE;
            } else {
                reasonEnum = ReportReason.ABUSE;
            }
        }
        Optional<Seller> store = userService.findOtherStore(storeId);
        Report report = new Report(user, store.get(), content, reasonEnum);
        userService.saveReport(report);
        rttr.addFlashAttribute("reportSuccess", "true");
        return "redirect:/store/info/" + storeId;
    }

    @GetMapping("/selling/{storeId}")
    public String getStoreSelling(@PathVariable("storeId") Long id, Model model) {

        return "user/otherstore/sellinghistory";
    }

    @GetMapping("/review/{storeId}")
    public String getStoreReview(@PathVariable("storeId") Long id, Model model) {
        List<Review> reviews = userService.findReviews(id);
        model.addAttribute("name", userService.findOtherStore(id).get().getName());
        Double starRate = Math.round(userService.getStoreStarRate(id) * 10) / 10.0;
        model.addAttribute("starRate", starRate);
        model.addAttribute("reviews", reviews);
        return "user/otherstore/reviews";
    }

    @GetMapping("/review/add/{storeId}")
    public String getAddReview(@PathVariable("storeId") Long id, Model model) {
        Optional<Seller> store = userService.findOtherStore(id);
        String name = store.get().getName();
        model.addAttribute("error", null);
        model.addAttribute("name", name);
        return "user/otherstore/addreview";
    }

    @PostMapping("/review/add/{storeId}")
    public String postAddReview(HttpServletRequest request,
                                HttpServletResponse response,
                                @PathVariable("storeId") Long id, Model model,
                                @RequestParam(value = "star", defaultValue = "") String star,
                                @RequestParam(value = "review", defaultValue = "") String review) throws IOException {
        log.info("star={}", star);
        log.info("review ={}", review);
        String error = null;
        Optional<Seller> store = userService.findOtherStore(id);
        String name = store.get().getName();
        if(star.equals("") && review.equals("")) {
            error = "별점을 선택하고 리뷰를 입력하세요.";
            model.addAttribute("error", error);
            model.addAttribute("name", name);
            return "user/otherstore/addreview";
        } else if(star.equals("")) {
            error = "별점을 선택하세요.";
            model.addAttribute("error", error);
            model.addAttribute("name", name);
            return "user/otherstore/addreview";
        } else if(review.equals("")) {
            error = "리뷰 내용을 입력하세요";
            model.addAttribute("error", error);
            model.addAttribute("name", name);
            return "user/otherstore/addreview";
        }

        // TODO
        // 리뷰 성공 로직
        Long userId = (Long) request.getSession().getAttribute("user");
        if(userId == null) {
            return "redirect:/";
        }
        User user = userService.findUser(userId);
        Review userReview = new Review(user, userService.findOtherStore(id).get(), review, Integer.valueOf(star));
        userService.saveReview(userReview);

        String message = "리뷰 등록이 완료되었습니다.";
        
        response.setContentType("text/html;charset=euc-kr");
        PrintWriter writer = response.getWriter();
        writer.println("<script>alert('"+ message +"'); window.close(); </script>");
        return null;
    }
}
