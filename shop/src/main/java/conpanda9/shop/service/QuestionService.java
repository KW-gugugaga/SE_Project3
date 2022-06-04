package conpanda9.shop.service;


import conpanda9.shop.domain.Notice;
import conpanda9.shop.domain.Question;
import conpanda9.shop.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    public List<Question> findAllQuestion() {
        List<Question> questions =new ArrayList<>(questionRepository.findAllQuestion());
        System.out.println(questions);
        return questions;
    }
}
