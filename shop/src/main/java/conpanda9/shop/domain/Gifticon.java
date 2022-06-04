package conpanda9.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Gifticon {

    @Id @GeneratedValue
    @Column(name = "gifticon_id")
    private Long id;

    private String name;

    /**
     * 상품의 판매자
     * 한명의 사용자가 여러개의 상품을 판매할 수 있음
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 상품의 카테고리
     * 한 카테고리에 여러개의 상품이 속할 수 있음
     * 한 상품이 여러 카테고리(ex. 식품, 생활)에 동시에 속할 수 없다고 가정!!
     */
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * 상품의 브랜드
     * 한 브랜드에 여러개의 상품이 속할 수 있음
     */
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;   // 상품의 브랜드(여러 상품이 하나의 브랜드에 속할 수 있음)

    private String image;   // 이미지 저장 경로
    private String description;   // 상품 설명
    private Long originalPrice;   // 원가
    private Long sellingPrice;   // 판매가
    private LocalDate expireDate;   // 유효기간
    private LocalDateTime uploadDate;   // 판매글 업로드 날짜(시간, 초 까지)
    private LocalDateTime lastModifiedDate;   // 판매글 마지막 수정 날짜(시간, 초 까지)

    public Gifticon(String name, User user, Category category, Brand brand, String image, String description, Long originalPrice, Long sellingPrice, LocalDate expireDate, LocalDateTime uploadDate, LocalDateTime lastModifiedDate) {
        this.name = name;
        this.user = user;
        this.category = category;
        this.brand = brand;   // 상품에 브랜드 연결
        brand.getGifticonList().add(this);   // 브랜드에 상품 연결
        this.image = image;
        this.description = description;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.expireDate = expireDate;
        this.uploadDate = uploadDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
