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
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 하나의 상점에 여러개의 리뷰가 달릴 수 있음
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    private String text;

    private Integer star;

    public Review(User user, Seller seller, String text, Integer star) {
        this.user = user;
        this.seller = seller;
        this.text = text;
        this.star = star;
        user.getReviewList().add(this);
    }
}
