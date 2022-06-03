package conpanda9.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class Review {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    /**
     * 한 사람이 여러개의 리뷰를 달 수 있음
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String text;

    private Integer star;
}
