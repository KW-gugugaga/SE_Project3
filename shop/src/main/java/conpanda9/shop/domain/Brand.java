package conpanda9.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Brand {

    @Id @GeneratedValue
    @Column(name = "brand_id")
    private Long id;

    private String name;

    private String img;

    /**
     * 브랜드와 카테고리를 연결
     * 하나의 브랜드는 하나의 카테고리에만 속한다고 가정
     * 카테고리 하나에 여러개의 브랜드가 속할 수 있음
     * ex.
     * 스타벅스(brand_id : 1) - 음료(category_id : 1)
     * 빽다방(brand_id : 2) - 음료(category_id : 1)
     * 이디야(brand_id : 3) - 음료(category_id : 1)
     */
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * 하나의 브랜드에 속한 여러 상품들을 담은 리스트
     * 양방향으로 저장
     * 가져오기 쉽게하려고
     */
    @OneToMany(mappedBy = "brand")
    private List<Gifticon> gifticonList = new ArrayList<>();

    public Brand(String name, Category category, String img) {
        this.name = name;
        this.category = category;   // 양방향으로 연결
        this.img = img;
        category.getBrandList().add(this);
    }
}
