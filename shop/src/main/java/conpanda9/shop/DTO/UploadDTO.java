package conpanda9.shop.DTO;

import conpanda9.shop.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor   // 기본 생성자
@AllArgsConstructor   // 모든 인자가 있는 생성자
public class UploadDTO {

    @NotBlank(message = "카테고리를 선택하세요.")
    private Category category;

    @NotBlank(message = "상품 내용을 입력하세요.")
    private String description;
}
