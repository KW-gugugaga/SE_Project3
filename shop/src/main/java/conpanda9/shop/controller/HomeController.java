package conpanda9.shop.controller;

import conpanda9.shop.DTO.LoginDTO;
import conpanda9.shop.DTO.QuestionDTO;
import conpanda9.shop.DTO.SearchDTO;
import conpanda9.shop.domain.*;
import conpanda9.shop.domain.gifticoncomparator.GifticonDateComparator;
import conpanda9.shop.service.AdminService;
import conpanda9.shop.service.ItemService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final UserService userService;
    private final ItemService itemService;
    private final AdminService adminService;

    @GetMapping("/")
    public String home(Model model) {
        log.info("get login");
        model.addAttribute("loginDTO", new LoginDTO());
        return "login";
    }

    @PostMapping("/")
    public String home(@Validated @ModelAttribute("loginDTO") LoginDTO loginDTO, BindingResult bindingResult,
                       HttpServletRequest request) {

        if(bindingResult.hasErrors()) {   // 필드 오류
            return "login";
        }

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
            HttpSession session = request.getSession();
            session.setAttribute("user", loginUser.getId());   // 세션 생성
            return "redirect:/main";   // 로그인 계정이 일반 회원이라면 main page로 이동
        }
    }

    @GetMapping("/main")
    public String main(Model model) {
        List<Category> categories = itemService.findAllCategory();
        model.addAttribute("categories", categories);
        return "main";
    }

    @GetMapping("/notice")
    public String getNotices(Model model) {
        List<Notice> notices = adminService.findAllNotice();
        model.addAttribute("notices", notices);
        return "user/notices/list";
    }

    @GetMapping("/notice/{noticeId}")
    public String getNotice(@PathVariable("noticeId") Long id, Model model) {
        Notice notice = adminService.findNotice(id);
        adminService.addNoticeCount(id);   // 조회수 1 증가
        model.addAttribute("notice", notice);
        return "user/notices/notice";
    }

    @GetMapping("/question")
    public String getQuestions(HttpServletRequest request, Model model) {
        Long id = (Long) request.getSession().getAttribute("user");
        if(id == null) {
            return "redirect:/";
        }
        List<Question> questions = userService.findQuestionByUser(id);
        model.addAttribute("questions", questions);
        return "user/questions/list";
    }

    @GetMapping("/question/{questionId}")
    public String getQuestion(@PathVariable("questionId") Long id, Model model) {
        Question question = userService.findQuestion(id);
        model.addAttribute("question", question);
        return "user/questions/question";
    }

    @GetMapping("/question/add")
    public String getQuestionAdd(Model model) {
        model.addAttribute("questionDTO", new QuestionDTO());
        return "user/questions/add";
    }

    @PostMapping("/question/add")
    public String postQuestionAdd(HttpServletRequest request,
                                  @Validated @ModelAttribute("questionDTO") QuestionDTO questionDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "user/questions/add";
        }

        Long id = (Long) request.getSession().getAttribute("user");
        User user = userService.findUser(id);
        Question question = new Question(questionDTO.getTitle(), questionDTO.getText(), user, LocalDateTime.now(), LocalDateTime.now(), null);
        userService.saveQuestion(question);
        return "redirect:/question";
    }

    @GetMapping("/question/edit/{questionId}")
    public String getQuestionEdit(@PathVariable("questionId") Long id, Model model) {
        Question question = userService.findQuestion(id);
        model.addAttribute("question", question);
        model.addAttribute("questionDTO", new QuestionDTO(question.getTitle(), question.getText()));
        return "user/questions/edit";
    }

    @PostMapping("/question/edit/{questionId}")
    public String postQuestionEdit(@PathVariable("questionId") Long id,
                                   @Validated @ModelAttribute("questionDTO") QuestionDTO questionDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "user/questions/edit";
        }
        log.info("origin title={}", userService.findQuestion(id).getTitle());
        log.info("new title={}", questionDTO.getTitle());
        userService.editQuestion(id, questionDTO);   // 문의사항 수정
        return "redirect:/question";
    }

    @GetMapping("/question/delete/{questionId}")
    public String getQuestionDelete(@PathVariable("questionId") Long id) {
        userService.deleteQuestion(id);   // 문의사항 삭제
        return "redirect:/question";
    }

    @GetMapping("/search")
    public String getSearch(Model model) {
        model.addAttribute("searchDTO", new SearchDTO());
        return "search/search";
    }
    

    @PostMapping("/search")
    public String postSearch(@Validated @ModelAttribute("searchDTO") SearchDTO searchDTO, BindingResult bindingResult,
                             HttpServletRequest request, Model model) {

        if(bindingResult.hasErrors()) {   // 필드 오류
            return "search/search";
        }
        String searchBrand = searchDTO.getSearchBrand();
        List<Gifticon> gifticons = itemService.searchByBrand(searchBrand);
        for (Gifticon gifticon : gifticons) {
            log.info(gifticon.getName());
        }
        model.addAttribute("gifticons", gifticons);
        return "search/search_result";
    }
}
