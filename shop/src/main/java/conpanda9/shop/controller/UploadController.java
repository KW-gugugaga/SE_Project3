package conpanda9.shop.controller;

import conpanda9.shop.DTO.NoticeDTO;
import conpanda9.shop.DTO.UploadDTO;
import conpanda9.shop.domain.*;
import conpanda9.shop.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UploadController {

    private final UploadService uploadService;

    @GetMapping("/upload")
    public String upload(Model model) {

        model.addAttribute("uploadDTO", new UploadDTO());
        return "user/upload";
    }
//    @PostMapping("/notice/add")
//    public String postNoticeAdd(@Validated @ModelAttribute("noticeDTO") NoticeDTO noticeDTO, BindingResult bindingResult,
//                                @RequestParam(value = "important", required = false) String important,
//                                Model model) {
//
//        if(bindingResult.hasErrors()) {   // 필드 오류
//            model.addAttribute("important", important);
//            return "admin/notices/add";
//        }
//
//        if(important == null) {
//            adminService.addNotice(noticeDTO, false);
//        } else {
//            adminService.addNotice(noticeDTO, true);
//        }
//
//        return "redirect:/admin/notice";
//    }
}
