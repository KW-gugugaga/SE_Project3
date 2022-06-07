package conpanda9.shop.controller;


import conpanda9.shop.domain.Question;
import conpanda9.shop.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/question")
    public String questions(Model model) {
        List<Question> questions = questionService.findAllQuestion();
        System.out.println(questions);
        System.out.println("값저장됨");
        model.addAttribute("questions", questions);
        return "user/questions/question";
    }


}
