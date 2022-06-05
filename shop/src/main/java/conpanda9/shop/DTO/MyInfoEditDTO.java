package conpanda9.shop.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class MyInfoEditDTO {

    @NotBlank(message = "아이디를 입력하세요.")
    private String loginId;

    @NotBlank(message = "닉네임을 입력하세요.")
    private String nickname;

    @NotBlank(message = "이메일을 입력하세요.")
    private String email;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "-를 포함한 핸드폰 번호를 형식에 맞추어 입력하세요.")
    private String phoneNumber;

    @NotBlank(message = "비밀번호 확인을 입력하세요")
    private String loginPwCheck;
}
