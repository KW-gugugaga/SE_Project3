package conpanda9.shop.controller;

import conpanda9.shop.DTO.NoticeDTO;
import conpanda9.shop.domain.Notice;
import conpanda9.shop.domain.Question;
import conpanda9.shop.domain.Report;
import conpanda9.shop.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
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
    public String notices(Model model) {
        List<Notice> notices = adminService.findAllNotice();
        model.addAttribute("notices", notices);
        return "admin/notices/list";
    }

    @GetMapping("/notice/{noticeId}")
    public String notice(@PathVariable("noticeId") Long id, Model model) {
        Notice notice = adminService.findNotice(id);
        log.info("notice important={}", notice.isImportant());
        model.addAttribute("notice", notice);
        return "admin/notices/notice";
    }

    @GetMapping("/notice/add")
    public String getNoticeAdd(Model model) {
        model.addAttribute("noticeDTO", new NoticeDTO());
        return "admin/notices/add";
    }

    @PostMapping("/notice/add")
    public String postNoticeAdd(@Validated @ModelAttribute("noticeDTO") NoticeDTO noticeDTO, BindingResult bindingResult,
                                @RequestParam(value = "important", required = false) String important,
                                Model model) {

        if(bindingResult.hasErrors()) {   // 필드 오류
            model.addAttribute("important", important);
            return "admin/notices/add";
        }

        if(important == null) {
            adminService.addNotice(noticeDTO, false);
        } else {
            adminService.addNotice(noticeDTO, true);
        }

        return "redirect:/admin/notice";
    }

    @GetMapping("/notice/edit/{noticeId}")
    public String getNoticeEdit(@PathVariable("noticeId") Long id, Model model) {
        Notice notice = adminService.findNotice(id);
        log.info("edit notice id={}", id);
        NoticeDTO noticeDTO = new NoticeDTO(notice.getTitle(), notice.getContents());
        model.addAttribute("noticeDTO", noticeDTO);
        model.addAttribute("important", notice.isImportant());
        return "admin/notices/edit";
    }

    @PostMapping("/notice/edit/{noticeId}")
    public String postNoticeEdit(@PathVariable("noticeId") Long id,
                                 @Validated @ModelAttribute("noticeDTO") NoticeDTO noticeDTO, BindingResult bindingResult,
                                 @RequestParam(value = "important", required = false) String importantValue,
                                 Model model) {
        log.info("edit notice id={}", id);
        log.info("notice important={}", importantValue);
        if(bindingResult.hasErrors()) {   // 필드오류
            if(importantValue == null) {
                model.addAttribute("important", false);
            } else {
                model.addAttribute("important", true);
            }
            return "admin/notices/edit";
        }

        if(importantValue == null) {
            adminService.updateNotice(id, noticeDTO, false);
        } else {
            adminService.updateNotice(id, noticeDTO, true);
        }
        return "redirect:/admin/notice";
    }

    @GetMapping("notice/delete/{noticeId}")
    public String deleteNotice(@PathVariable("noticeId") Long id) {
        log.info("delete notice id={}", id);
        adminService.deleteNotice(id);
        return "redirect:/admin/notice";
    }
}
