package conpanda9.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Shared {   // 나눔 내역

    @Id @GeneratedValue
    @Column(name = "shared_id")
    private Long id;

    /**
     * 나눔 내역
     * 한 사용자가 여러번 나눔 받을 수 있음
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 나눔 내역
     * 한 판매자가 여러번 나눔할 수 있음
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    /**
     * 한 나눔 상품은 한번만 나눔 가능
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "share_id")
    private Share share;

    public Shared(User user, Seller seller, Share share) {
        this.user = user;
        this.seller = seller;
        this.share = share;
        seller.getSharedList().add(this);
        share.setShared(this);
    }
}
