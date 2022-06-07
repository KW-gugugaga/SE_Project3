package conpanda9.shop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    
    @NotBlank(message = "문의사항 제목을 입력하세요.")
    private String title;
    
    @NotBlank(message = "문의사항 내용을 입력하세요.")
    private String text;
}
