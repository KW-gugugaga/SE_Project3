package conpanda9.shop.DTO;

import conpanda9.shop.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor   // 기본 생성자
@AllArgsConstructor   // 모든 인자가 있는 생성자
public class UploadDTO {

    @NotBlank(message = "상품 이름을 입력하세요.")
    private String name;

    @NotBlank(message = "카테고리를 선택하세요.")
    private String categoryId;

    @NotBlank(message = "브랜드를 선택하세요.")
    private String brandName;

    @NotBlank(message = "상품 원가를 입력하세요.")
    private String originalPrice;

    @NotBlank(message = "상품 판매가를 입력하세요.")
    private String sellingPrice;

    @NotNull(message = "good")
    private String expireDate;

    @NotBlank(message = "상품 내용을 입력하세요.")
    private String description;
}
