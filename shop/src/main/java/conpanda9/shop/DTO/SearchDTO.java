package conpanda9.shop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor   // 기본 생성자
@AllArgsConstructor   // 모든 인자가 있는 생성자
public class SearchDTO {

    @NotBlank(message = "검색어를 입력하세요.")
    private String searchKind;
    private String searchWhat;

}
