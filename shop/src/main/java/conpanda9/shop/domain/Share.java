package conpanda9.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Share {

    @Id @GeneratedValue
    @Column(name = "share_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    /**
     * 한명의 판매자가 여러개의 상품을 나눔할 수 있음
     */
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    private String image;
    private String description;
    private Long originalPrice;
    private LocalDateTime uploadDate;
    private LocalDateTime lastModifiedDate;
    private LocalDate expireDate;

    @Enumerated(EnumType.STRING)
    private ShareState state;

    /**
     * 나눔 받은 사람 - 나눔 안된 상품이면 null로
     */

    public Share(String name, Brand brand, Seller seller, String image, String description, Long originalPrice, LocalDateTime uploadDate, LocalDateTime lastModifiedDate, LocalDate expireDate) {
        this.name = name;
        this.brand = brand;
        this.seller = seller;
        this.image = image;
        this.description = description;
        this.originalPrice = originalPrice;
        this.uploadDate = uploadDate;
        this.lastModifiedDate = lastModifiedDate;
        this.expireDate = expireDate;
        this.state = ShareState.Sharing;   // 기본은 나눔중
    }
}
