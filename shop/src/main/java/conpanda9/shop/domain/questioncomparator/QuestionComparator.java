package conpanda9.shop.domain.questioncomparator;

import conpanda9.shop.domain.Question;

import java.util.Comparator;

public class QuestionComparator implements Comparator<Question> {
    @Override
    public int compare(Question o1, Question o2) {
        if(o1.getUploadDate().isBefore(o2.getUploadDate())) {
            return 1;   // 더 최신순인 문의사항이 더 앞으로
        } else {
            return -1;
        }
    }
}
