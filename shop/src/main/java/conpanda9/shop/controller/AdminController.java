package conpanda9.shop.controller;

import conpanda9.shop.domain.Question;
import conpanda9.shop.domain.Report;
import conpanda9.shop.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/main")
    public String main() {

        return "admin/main";
    }

    @GetMapping("/question")
    public String question(Model model) {
        List<Question> questions = adminService.findAllQuestion();
        model.addAttribute("questions", questions);
        return "admin/question";
    }

    @GetMapping("/report")
    public String report(Model model) {
        List<Report> reports = adminService.findAllReport();
        model.addAttribute("reports", reports);
        return "admin/report";
    }

    @GetMapping("/notice")
    public String notice() {
        return "admin/notice";
    }
}
