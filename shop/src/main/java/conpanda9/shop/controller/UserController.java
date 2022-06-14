package conpanda9.shop.controller;

import conpanda9.shop.DTO.FindIdPwDTO;
import conpanda9.shop.DTO.JoinDTO;
import conpanda9.shop.DTO.MyInfoEditDTO;
import conpanda9.shop.DTO.PwEditDTO;
import conpanda9.shop.domain.*;
import conpanda9.shop.domain.soldcomparator.SoldDateComparator;
import conpanda9.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("joinDTO", new JoinDTO());
        return "user/join";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute("joinDTO") JoinDTO joinDTO, BindingResult bindingResult,
                       RedirectAttributes rttr) {
        /**
         * @Validated, 검사 객체, BindingResult 세트로 같이 써야함
         * 검사 객체에 Validation annotation으로 달아놓은 것 검사
         * message에 등록한 문구 출력
         */

        if(bindingResult.hasErrors()) {   // 필드 에러
            return "user/join";
        }

        if(!joinDTO.getLoginPw().equals(joinDTO.getLoginPwCheck())) {
            bindingResult.addError(new FieldError("joinDTO", "loginPwCheck", joinDTO.getLoginPwCheck(), false, null, null,  "비밀번호 확인이 일치하지 않습니다."));
            return "user/join";
        }

        userService.existsCheck(joinDTO, bindingResult);   // 중복 검사
        log.info("field errors={}", bindingResult.getFieldErrors());

        if(bindingResult.hasErrors()) {
            return "user/join";
        }

        User newUser = new User(joinDTO.getLoginId(), joinDTO.getLoginPw(), joinDTO.getNickname(), joinDTO.getEmail(), joinDTO.getPhoneNumber());

        userService.save(newUser);
        rttr.addFlashAttribute("joinSuccess", "true");
        return "redirect:/";
    }

    @GetMapping("/find/id")
    public String getFindUserId(Model model) {
        model.addAttribute("findIdDTO", new FindIdPwDTO());
        return "user/findid";
    }

    @PostMapping("/find/id")
    public String postFindUserId(@Validated @ModelAttribute("findIdDTO") FindIdPwDTO findIdDTO, BindingResult bindingResult,
                                 Model model) {
        if(bindingResult.hasErrors()) {
            return "user/findid";
        }
        Optional<User> findUser = userService.findId(findIdDTO.getInputFirst(), findIdDTO.getInputSecond());
        if(findUser.isPresent()) {
            model.addAttribute("findUser", findUser.get());
        } else {
            model.addAttribute("findUser", null);
        }
        return "user/findidresult";
    }

    @GetMapping("/find/pw")
    public String getFindUserPw(Model model) {
        model.addAttribute("findPwDTO", new FindIdPwDTO());
        return "user/findpw";
    }

    @PostMapping("/find/pw")
    public String postFindPw(@Validated @ModelAttribute("findPwDTO") FindIdPwDTO findPwDTO, BindingResult bindingResult,
                             Model model) {
        if(bindingResult.hasErrors()) {
            return "user/findpw";
        }
        Optional<User> findUser = userService.findPw(findPwDTO.getInputFirst(), findPwDTO.getInputSecond());
        if(findUser.isPresent()) {
            model.addAttribute("findUser", findUser.get());
        } else {
            model.addAttribute("findUser", null);
        }
        return "user/findpwresult";
    }

    @GetMapping("/info/myinfo")
    public String getMyInfo(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("user");
        if(userId == null) {
            return "redirect:/";
        } else {
            User user = userService.findUser(userId);
            log.info("myinfo user={}", user.getId());
            model.addAttribute("user", user);
        }
        return "user/info/myinfo";
    }

    @GetMapping("/info/edit/{userId}")
    public String getUserEdit(@PathVariable("userId") Long userId, HttpServletRequest request, Model model) {
        Long id = (Long) request.getSession().getAttribute("user");
        if(id == null || !Objects.equals(userId, id)) {   // session값과 다르거나 session이 없는 경우
            return "redirect:/";
        }

        log.info("user edit id={}", id);
        model.addAttribute("editDTO", new MyInfoEditDTO());
        model.addAttribute("id", id);
        return "user/info/editinfo";
    }

    @PostMapping("/info/edit/{userId}")
    public String postUserEdit(@PathVariable("userId") Long id, Model model,
                               @Validated @ModelAttribute("editDTO") MyInfoEditDTO editDTO, BindingResult bindingResult) {

        log.info("post user edit id={}", id);
        if(bindingResult.hasErrors()) {
            model.addAttribute("id", id);
            return "user/info/editinfo";
        }

        userService.existCheck(editDTO, bindingResult);
        if(bindingResult.hasErrors()) {
            model.addAttribute("id", id);
            return "user/info/editinfo";
        }

        if(!editDTO.getLoginPwCheck().equals(userService.findUser(id).getLoginPw())) {
            model.addAttribute("id", id);
            bindingResult.addError(new FieldError("editDTO", "loginPwCheck", "비밀번호가 일치하지 않습니다."));
            return "user/info/editinfo";
        }

        userService.editUser(id, editDTO);
        return "redirect:/user/info/myinfo";
    }

    @GetMapping("/info/edit/pw/{userId}")
    public String getEditPw(@PathVariable("userId") Long userId, Model model, HttpServletRequest request) {
        Long id = (Long) request.getSession().getAttribute("user");
        if(id == null || !id.equals(userId)) {
            return "redirect:/";
        }
        model.addAttribute("id", id);
        model.addAttribute("editDTO", new PwEditDTO());
        return "user/info/editpw";
    }

    @PostMapping("/info/edit/pw/{userId}")
    public String postEditPw(@PathVariable("userId") Long id, Model model,
                             @Validated @ModelAttribute("editDTO") PwEditDTO editDTO, BindingResult bindingResult) {
        log.info("pw edit post id={}", id);
        if(bindingResult.hasErrors()) {
            model.addAttribute("id", id);
            return "user/info/editpw";
        }
        
        if(!editDTO.getNewPw().equals(editDTO.getNewPwCheck())) {
            bindingResult.addError(new FieldError("editDTO", "newPwCheck", "비밀번호가 일치하지 않습니다."));
        }

        if(!editDTO.getOriginalPwCheck().equals(userService.findUser(id).getLoginPw())) {
            model.addAttribute("id", id);
            bindingResult.addError(new FieldError("editDTO", "originalPwCheck", "기존 비밀번호가 틀립니다."));
            return "user/info/editpw";
        }

        userService.editPw(id, editDTO.getNewPw());
        return "redirect:/user/myinfo";
    }

    @GetMapping("/store")
    public String getStore(HttpServletRequest request, Model model) {
        // TODO
        // 상점 정보 띄우기
        Long totalSellPrice = 0L;
        Optional<Seller> storeOptional = userService.findStore((Long) request.getSession().getAttribute("user"));
        if(storeOptional.isPresent()) {   // 상점이 있을 경우 총 판매 금액 계산
            totalSellPrice = userService.getTotalSellPrice(storeOptional.get().getId());
            Double starRate = Math.round(userService.getStoreStarRate(storeOptional.get().getId()) * 10) / 10.0;
            model.addAttribute("store", storeOptional.get());
            model.addAttribute("starRate", starRate);
        } else {
            model.addAttribute("store", null);
        }
        model.addAttribute("totalSellPrice", totalSellPrice);
        return "user/store/info";
    }

    @GetMapping("/store/add")
    public String getStoreAdd(Model model) {
        model.addAttribute("error", null);
        return "user/store/add";
    }

    @PostMapping("/store/add")
    public String postStoreAdd(HttpServletRequest request,
                               @RequestParam("name") String name, Model model) {

        log.info("add store name={}", name);
        String error = null;

        if(name.equals("")) {
            log.info("no store name");
            error = "상점 이름을 입력하세요";
            model.addAttribute("error", error);
            return "/user/store/add";
        }

        Optional<Seller> seller = userService.existCheck(name);
        if(seller.isPresent()) {   // 중복 상점이 존재할 경우
            error = "상점 이름이 이미 존재합니다.";
            model.addAttribute("error", error);
            return "/user/store/add";
        }

        Seller store = new Seller(name, userService.findUser((Long) request.getSession().getAttribute("user")));
        userService.saveStore(store);   // 상점 등록
        model.addAttribute("error", error);
        return "redirect:/user/store";
    }

    @GetMapping("/store/selling")
    public String getSelling(HttpServletRequest request, Model model) {
        Long id = (Long) request.getSession().getAttribute("user");
        Optional<Seller> store = userService.findStore(id);
        // TODO
        // 판매내역에서 총 판매 금액 계산해서 띄워야함
        Long totalSellPrice = 0L;
        if(store.isPresent()) {
            List<Gifticon> sellings = userService.findSellings(store.get().getId());
            model.addAttribute("sellings", sellings);   // 판매중 내역
            // 판매중인 내역이 있다면 판매 완료 아이템 있을 가능성도 있는 것
            // 일단 계산
            totalSellPrice = userService.getTotalSellPrice(store.get().getId());
        }
        model.addAttribute("totalSellPrice", totalSellPrice);
        model.addAttribute("store", store);
        return "user/store/sellinghistory";
    }

    @GetMapping("/store/sold")
    public String getSold(HttpServletRequest request, Model model) {
        Long id = (Long) request.getSession().getAttribute("user");
        Optional<Seller> store = userService.findStore(id);
        // TODO
        // 일단 팔린 상품 팔린 날짜 최신순 정렬
        List<Sold> solds = userService.findSolds(store.get().getId());   // 팔린 상품
        solds.sort(new SoldDateComparator());
        Long totalSellPrice = userService.getTotalSellPrice(store.get().getId());
        model.addAttribute("solds", solds);   // 판매 완료 내역
        model.addAttribute("totalSellPrice", totalSellPrice);
        return "user/store/soldhistory";
    }

    @GetMapping("/store/buy")
    public String getBuy(HttpServletRequest request, Model model) {   // 구매내역
        // user id의 구매내역
        List<Sold> buys = userService.findBuys((Long) request.getSession().getAttribute("user"));
        buys.sort(new SoldDateComparator());   // 더 최신에 구매한 내역
        model.addAttribute("buys", buys);
        return "user/store/buyhistory";
    }

    @GetMapping("/store/review")
    public String getStoreReview(HttpServletRequest request, Model model) {
        Long id = (Long) request.getSession().getAttribute("user");
        Optional<Seller> store = userService.findStore(id);
        if(store.isPresent()) {
            List<Review> reviews = userService.findReviews(store.get().getId());   // 상점에 달린 리뷰들
            model.addAttribute("reviews", reviews);
            return "user/store/review";
        } else {
            return "redirect:/";
        }
    }
    @GetMapping("/alarms")
    public String getAlarm(HttpServletRequest request, Model model){
        Long id = (Long) request.getSession().getAttribute("user");
        if(id == null) {
            return "redirect:/";
        }
        log.info("id={}",id);

        List<Alarm> alarm = userService.findAlarm(id);
        for (Alarm alarm1 : alarm) {
            log.info("alarm={}",alarm1.getTitle());
            log.info("alarm.checked={}",alarm1.isChecked());

        }
        Long a_cnt = userService.countAlarm(id);
        log.info("안읽은거 ;={} ",a_cnt);

        model.addAttribute("alarms",alarm);
        model.addAttribute("a_cnt",a_cnt);
        return "user/alarms/alarm";
    }
    @PostMapping("/alarms/{alarmId}")
    public String PostAlarm(@PathVariable("alarmId") Long id, Model model, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=euc-kr");
        PrintWriter out = response.getWriter();
        String message="알림 확인 완료";
        out.println("<script>alert('"+ message +"'); window.close(); </script>");
        out.flush();
        Alarm alarm = userService.updateAlarm(id);
        log.info("alarm.checked={}",alarm.isChecked());
        log.info("alarm.내용={}",alarm.getTitle());
        return "redirect:/user/alarms";
    }
    @GetMapping("/alarm/detail/{alarmID}")
    public String getAlarmDetail(@PathVariable("alarmID") Long id, Model model) {
        Alarm alarm= userService.findOneAlarm(id);
        log.info("alarm.title={}",alarm.getTitle());
        model.addAttribute("alarm", alarm);
        return "user/alarms/detail";
    }

    @GetMapping("/wallet")
    public String getWallet(HttpServletRequest request, Model model) {
        Long userId = (Long) request.getSession().getAttribute("user");
        if(userId == null) {
            return "redirect:/";
        }
        User user = userService.findUser(userId);
        Long point = user.getPoint();
        Long totalSellPrice = 0L;
        Optional<Seller> store = userService.findStore(userId);
        if(store.isPresent()) {
            totalSellPrice = userService.getTotalSellPrice(store.get().getId());
        }
        Long totalBuyPrice = userService.getTotalBuyPrice(userId);
        model.addAttribute("point", point);
        model.addAttribute("user", user);
        model.addAttribute("totalSellPrice", totalSellPrice);   // 총 판매 금액
        model.addAttribute("totalBuyPrice", totalBuyPrice);   // 총 판매 금액
        return "user/wallet";
    }

}
