package conpanda9.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class Seller {

    @Id @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    private String name;   // 상품 이름

    /**
     * 상품 주인
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long heart;   // 찜
    private Long share;   // 나눔 횟수

    public Seller(String name, User user) {
        this.name = name;
        this.user = user;
        this.heart = 0L;
        this.share = 0L;
    }
}
