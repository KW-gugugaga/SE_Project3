package conpanda9.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class Question {

    @Id @GeneratedValue
    @Column(name = "question_id")
    private Long id;

    private String text;   // 내용

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String answer;   // 관리자 답변 (null 가능, 없어도됨)
}
