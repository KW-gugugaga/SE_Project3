package conpanda9.shop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {

    @NotBlank(message = "공지 제목을 입력하세요.")
    private String title;

    @NotBlank(message = "공지 내용을 입력하세요.")
    private String contents;
}
