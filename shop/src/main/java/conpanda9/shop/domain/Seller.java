package conpanda9.shop.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Seller {

    @Id
    @Column(name = "seller_id")
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long heart;   // 찜
    private Long share;   // 나눔 횟수
}
