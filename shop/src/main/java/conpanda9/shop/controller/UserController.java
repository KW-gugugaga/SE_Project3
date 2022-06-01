package conpanda9.shop.controller;

import conpanda9.shop.DTO.JoinDTO;
import conpanda9.shop.domain.User;
import conpanda9.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String join(@Validated @ModelAttribute("joinDTO") JoinDTO joinDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {   // 필드 에러
            return "user/join";
        }

        userService.existsCheck(joinDTO, bindingResult);   // 중복 검사
        log.info("field errors={}", bindingResult.getFieldErrors());

        if(bindingResult.hasErrors()) {
            return "user/join";
        }

        User newUser = new User(joinDTO.getLoginId(), joinDTO.getLoginPw(), joinDTO.getNickname(), joinDTO.getEmail(), joinDTO.getPhoneNumber());

        userService.save(newUser);

        return "redirect:/login";
    }

}
