package conpanda9.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class Question {

    @Id @GeneratedValue
    @Column(name = "question_id")
    private Long id;

    private String title;   // 제목

    private String text;   // 내용

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime uploadDate;   // 업로드 날짜
    private LocalDateTime lastModifiedDate;   // 마지막 수정 날짜

    private String answer;   // 관리자 답변 (null 가능, 없어도됨)

    public Question(String title, String text, User user, LocalDateTime uploadDate, LocalDateTime lastModifiedDate) {
        this.title = title;
        this.text = text;
        this.user = user;
        this.uploadDate = uploadDate;
        this.lastModifiedDate = lastModifiedDate;
        this.answer = null;
    }
}
