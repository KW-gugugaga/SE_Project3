package conpanda9.shop.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FindIdPwDTO {

    @NotBlank(message = "회원 정보를 입력하세요.")
    private String inputFirst;

    @NotBlank(message = "회원 정보를 입력하세요.")
    private String inputSecond;
}
