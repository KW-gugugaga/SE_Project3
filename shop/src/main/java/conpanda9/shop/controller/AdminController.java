package conpanda9.shop.controller;

import conpanda9.shop.DTO.NoticeDTO;
import conpanda9.shop.domain.*;
import conpanda9.shop.domain.gifticoncomparator.GifticonDateComparator;
import conpanda9.shop.domain.questioncomparator.QuestionComparator;
import conpanda9.shop.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private final AdminService adminService;

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

        if(bindingResult.hasErrors()) {   // ?????? ??????
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
        if(bindingResult.hasErrors()) {   // ????????????
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

    @GetMapping("/notice/delete/{noticeId}")
    public String deleteNotice(@PathVariable("noticeId") Long id) {
        log.info("delete notice id={}", id);
        adminService.deleteNotice(id);
        return "redirect:/admin/notice";
    }

    @GetMapping("/question")
    public String getQuestions(Model model) {
        List<Question> questions = adminService.findAllQuestion();
        questions.sort(new QuestionComparator());   // question ????????? ?????? ????????? ??????
        model.addAttribute("questions", questions);
        return "admin/questions/list";
    }

    @GetMapping("/question/{questionId}")
    public String getQuestion(@PathVariable("questionId") Long id, Model model) {
        Question question = adminService.findQuestion(id);
        log.info("question answer={}", question.getAnswer());
        model.addAttribute("question", question);
        model.addAttribute("error", null);
        return "admin/questions/question";
    }

    @PostMapping("/question/add/answer/{questionId}")
    public String postQuestionAddAnswer(@PathVariable("questionId") Long id,
                                        @RequestParam("answer") String answer, Model model) {
        log.info("answer={}", answer);
        String error = null;
        if(answer.equals("")) {
            error = "?????? ????????? ??????????????????";
            model.addAttribute("error", error);
            model.addAttribute("question", adminService.findQuestion(id));
            return "/admin/questions/question";
        }

        adminService.addAnswer(id, answer);
        return "redirect:/admin/question/" + id;
    }

    @GetMapping("/question/edit/answer/{questionId}")
    public String getQuestionEditAnswer(@PathVariable("questionId") Long id, Model model) {
        Question question = adminService.findQuestion(id);
        log.info("edit question id={}", id);
        model.addAttribute("question", question);
        model.addAttribute("error", null);
        return "admin/questions/edit";
    }

    @PostMapping("/question/edit/answer/{questionId}")
    public String postQuestionEditAnswer(@PathVariable("questionId") Long id,
                                         @RequestParam("answer") String answer, Model model) {
        String error = null;
        if(answer.equals("")) {
            error = "?????? ????????? ???????????????";
            model.addAttribute("question", adminService.findQuestion(id));
            model.addAttribute("error", error);
            return "admin/questions/edit";
        }

        adminService.editAnswer(id, answer);
        return "redirect:/admin/question/" + id;
    }

    @GetMapping("/user")
    public String users(Model model) {
        List<User> users = adminService.findAllUser();
        users.remove(0);   // admin ??????
        model.addAttribute("users", users);
        return "admin/user/userlist";
    }

    @GetMapping("/user/delete/{userId}")
    public String deleteUser(@PathVariable("userId") Long id){
        log.info("delete user id={}", id);
        /**
         * ????????? ????????? ???????????? ?????? ??????
         * sold, shared, seller?????? user??? null??? ??????
         * ?????? ???????????? ??????????????? ?????? ???????????? ??????????????? ??????????????? ??????
         * ?????? ????????? seller??? ????????????????????? ?????? ?????? ????????? ????????????
         */
        // TODO
        // ???????????? ????????? ???????????? ???????????? ?????????????????? ????????? ?????? ?????? ????????? ?????? ????????? ??????

        // TODO
        // ?????? ?????? ?????? ?????? ??????
        adminService.setNullUserActHistory(id);
        /**
         * ?????? ?????? ?????? ??? ?????? ?????? user ??????
         * ????????? review, question, alarm, review??? ?????????!
         */
        adminService.deleteUser(id);
        return "redirect:/admin/user";
    }

    @GetMapping("/report")
    public String getReportList(Model model,
                                @ModelAttribute(value = "completeSuccess") String completeSuccess) {
        List<Report> reports = adminService.findAllReport();
        model.addAttribute("reports", reports);
        model.addAttribute("completeSuccess", completeSuccess);
        return "admin/reports/list";
    }

    @GetMapping("/report/{reportId}")
    public String getReport(@PathVariable("reportId") Long id, Model model) {
        Report report = adminService.findReport(id);
        // TODO
        // ???????????? enum value ????????????
        String reportReason = null;
        if(report.getReportReason().equals(ReportReason.FAKE)) {
            reportReason = "?????? ??????";
        } else if(report.getReportReason().equals(ReportReason.ABUSE)) {
            reportReason = "???????????? ??????";
        } else if (report.getReportReason().equals(ReportReason.DUPLICATE)) {
            reportReason = "?????? ??????";
        } else {
            reportReason = "??????";
        }
        model.addAttribute("report", report);
        model.addAttribute("reportReason", reportReason);
        return "admin/reports/report";
    }

    @GetMapping("/report/complete/{reportId}")
    public String getReportComplete(@PathVariable("reportId") Long id, Model model) {
        Report report = adminService.findReport(id);
        String reportReason = null;
        if(report.getReportReason().equals(ReportReason.FAKE)) {
            reportReason = "?????? ??????";
        } else if(report.getReportReason().equals(ReportReason.ABUSE)) {
            reportReason = "???????????? ??????";
        } else if (report.getReportReason().equals(ReportReason.DUPLICATE)) {
            reportReason = "?????? ??????";
        } else {
            reportReason = "??????";
        }
        model.addAttribute("reportReason", reportReason);
        model.addAttribute("error", null);
        model.addAttribute("report", report);
        return "admin/reports/complete";
    }

    @PostMapping("/report/complete/{reportId}")
    public String postReportComplete(@PathVariable("reportId") Long id, Model model,
                                     RedirectAttributes rttr,
                                     @RequestParam("completeAct") String completeAct,
                                     @RequestParam(value = "act", defaultValue = "") String act) {
        String error = null;
        Report report = adminService.findReport(id);
        if(completeAct.equals("extra")) {
            if(act.equals("")) {
                error = "?????? ?????? ????????? ??????????????????.";
                String reportReason = null;
                if(report.getReportReason().equals(ReportReason.FAKE)) {
                    reportReason = "?????? ??????";
                } else if(report.getReportReason().equals(ReportReason.ABUSE)) {
                    reportReason = "???????????? ??????";
                } else if (report.getReportReason().equals(ReportReason.DUPLICATE)) {
                    reportReason = "?????? ??????";
                } else {
                    reportReason = "??????";
                }
                model.addAttribute("reportReason", reportReason);
                model.addAttribute("report", report);
                model.addAttribute("error", error);
                return "admin/reports/complete";
            }
        }
        // TODO
        // ???????????? ?????? ??? ????????? ??????
        // ?????? ?????? ????????? ?????? ????????? ????????? ???????????????
        adminService.updateReportComplete(id);
        Alarm alarm = new Alarm(report.getUser(), LocalDateTime.now(), "?????? ?????? ?????? ??????", "?????? ?????? ??????", false);
        adminService.saveReport(alarm);
        rttr.addFlashAttribute("completeSuccess", "true");
        return "redirect:/admin/report";
    }

    @GetMapping("/item")
    public String getItemList(Model model) {
        //?????? ???????????? ?????? ?????????
        // ???????????? ???????????? ??????
        List<Gifticon> gifticons = adminService.findAllGitficons().stream()
                .filter(g -> g.getState().equals(GifticonState.Selling))
                .sorted(new GifticonDateComparator()).collect(Collectors.toList());
        model.addAttribute("gifticons", gifticons);
        return "admin/items/list";
    }

    @GetMapping("/item/delete/{gifticonId}")
    public String getDeleteGifticon(@PathVariable("gifticonId") Long id) {
        log.info("gifticon id={}", id);
        adminService.setNullGifticon(id);
        return "redirect:/admin/item";
    }


    @GetMapping("/main")
    public String GetVisualization(Model model) {

        Map<String, Integer> categoriesData = new LinkedHashMap<String, Integer>();
        categoriesData.put("??????", 25);
        categoriesData.put("??????", 15);
        categoriesData.put("????????????/??????/???", 9);
        categoriesData.put("??????", 3);
        categoriesData.put("???????????????", 5);
        categoriesData.put("?????????", 8);
        categoriesData.put("?????????", 7);
        categoriesData.put("???????????????/??????", 9);
        categoriesData.put("??????", 5);

        model.addAttribute("categoriesData", categoriesData);

        Map<String, Integer> brandData = new LinkedHashMap<String, Integer>();
        brandData.put("????????????",13);
        brandData.put("bhc",9);
        brandData.put("??????????????????",7);
        brandData.put("??????????????????",5);
        brandData.put("????????????",2);
        model.addAttribute("brandData", brandData);
        return "admin/static";
    }
}
