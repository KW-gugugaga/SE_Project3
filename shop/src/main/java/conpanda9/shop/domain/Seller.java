package conpanda9.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class Seller {

    @Id @GeneratedValue
    @Column(name = "seller_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long heart;   // 찜
    private Long share;   // 나눔 횟수
}
