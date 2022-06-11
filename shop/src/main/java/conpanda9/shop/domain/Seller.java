package conpanda9.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long heart;   // 찜
    private Long share;   // 나눔 횟수

    @OneToMany(mappedBy = "seller", cascade = CascadeType.REMOVE)
    private List<Gifticon> gifticonList = new ArrayList<>();

    @OneToMany(mappedBy = "seller", cascade = CascadeType.REMOVE)
    private List<Share> shareList = new ArrayList<>();

    @OneToMany(mappedBy = "seller", cascade = CascadeType.REMOVE)
    private List<Shared> sharedList = new ArrayList<>();

    public Seller(String name, User user) {
        this.name = name;
        this.user = user;
        this.heart = 0L;
        this.share = 0L;
    }
}
