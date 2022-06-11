package conpanda9.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class Sold {   // 판매 내역

    @Id @GeneratedValue
    @Column(name = "sold_id")
    private Long id;   // 거래 내역 id

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gifticon_id")
    private Gifticon gifticon;   // 거래 상품

    /**
     * 해당 거래의 판매자
     * 한명의 거래자가 여러펀의 거래를 진행할 수 있음(여러번 팔 수 있음)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    /**
     * 해당 거래의 구매자
     * 한명의 구매자가 여러펀의 거래를 진행할 수 있음(여러번 살 수 있음)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long soldPrice;   // 거래 완료 가격
    private LocalDateTime soldDate;   // 거래 완료 날짜

    public Sold(Gifticon gifticon, Seller seller, User user, Long soldPrice, LocalDateTime soldDate) {
        this.gifticon = gifticon;
        this.seller = seller;
        this.user = user;
        this.soldPrice = soldPrice;
        this.soldDate = soldDate;
        gifticon.setSold(this);
    }
}
