package conpanda9.shop.controller;

import conpanda9.shop.domain.Report;
import conpanda9.shop.domain.ReportReason;
import conpanda9.shop.domain.Seller;
import conpanda9.shop.domain.User;
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
        Optional<Seller> store = userService.findOtherStore(id);
        Double starRate = Math.round(userService.getStoreStarRate(id) * 10) / 10.0;
        log.info("starRate={}", starRate);

        if(store.isPresent()) {
            model.addAttribute("store", store.get());
            model.addAttribute("starRate", starRate);
            model.addAttribute("reportSuccess", reportSuccess);
            log.info("reportSuccess={}", reportSuccess);
            return "user/otherstore/info";
        } else {
            return "redirect:/";
        }
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
        if(reportReason.equals("default")) {
            error = "신고 사유를 선택해주세요.";
            model.addAttribute("error", error);
            return "user/otherstore/report";
        } else if(reportReason.equals("extra")) {
            if(extraReason.equals("")) {
                error = "기타 신고 사유를 입력해주세요.";
                model.addAttribute("error", error);
                return "user/otherstore/report";
            }
        } else {
            if(gifticon.equals("")) {
                error = "신고하고자 하는 상품명을 입력해주세요.";
                model.addAttribute("error", error);
                return "user/otherstore/report";
            }
        }

        // TODO
        // 신고 내역 등록
        Long userId = (Long) request.getSession().getAttribute("user");
        if(userId == null) {
            return "redirect:/";
        }
        User user = userService.findUser(userId);
        ReportReason reasonEnum = null;
        String content = null;
        if(reportReason.equals("extra")) {
            reasonEnum = ReportReason.EXTRA;
            content = extraReason;
        } else {
            content = gifticon;
            if(reportReason.equals("duplicate")) {
                reasonEnum = ReportReason.DUPLICATE;
            } else if(reportReason.equals("fake")) {
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
}
