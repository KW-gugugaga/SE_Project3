package conpanda9.shop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class NoticeUpdateDTO {

    @NotBlank(message = "공지 제목을 입력하세요.")
    private String title;

    @NotBlank(message = "공지 내용을 입력하세요.")
    private String contents;
}
