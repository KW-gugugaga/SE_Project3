package conpanda9.shop.controller;

import conpanda9.shop.DTO.LoginDTO;
import conpanda9.shop.domain.User;
import conpanda9.shop.service.AdminService;
import conpanda9.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final UserService userService;
    private final AdminService adminService;

    @GetMapping("/")
    public String defaultMain() {
        return "login";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/login")
    public String home(Model model) {
        log.info("get login");
        model.addAttribute("loginDTO", new LoginDTO());
        return "login";
    }

    @PostMapping("/login")
    public String home(@Validated @ModelAttribute("loginDTO") LoginDTO loginDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {   // 필드 오류
            return "login";
        }

        //TODO
        //아이디 비밀번호 일치 여부 확인
        User loginUser = userService.login(loginDTO);

        if(loginUser == null) {   // 아이디, 비밀번호 일치 회원이 없을 경우
            bindingResult.addError(new ObjectError("loginDTO", "아이디 또는 비밀번호가 일치하지 않습니다."));
            return "login";
        }

        log.info("loginUser loginId={}", loginUser.getLoginId());
        log.info("loginUser loginPw={}", loginUser.getLoginPw());

        if(loginUser.getLoginId().equals("admin") && loginUser.getLoginPw().equals("admin!")) {
            return "redirect:admin/main";   // 로그인 계정이 admin이라면 admin page로 이동
        } else {
            return "redirect:/main";   // 로그인 계정이 일반 회원이라면 main page로 이동
        }
    }

}
