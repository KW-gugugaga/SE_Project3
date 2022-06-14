package conpanda9.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;
    private String img;

    /**
     * 카테고리 하나에 여러개의 브랜드가 속할 수 있음
     * 각 카테고리에 속하는 브랜드를 저장하기 위한 list
     * 가져오기 편하게 양방향으로 연결
     */
    @OneToMany(mappedBy = "category")
    private List<Brand> brandList = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<Gifticon> gitficonList = new ArrayList<>();

    public Category(String name, String img) {
        this.name = name;
        this.img = img;
    }

}
