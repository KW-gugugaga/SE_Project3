package conpanda9.shop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor   // 기본 생성자
@AllArgsConstructor   // 모든 인자가 있는 생성자
public class JoinDTO {

    @NotBlank(message = "아이디를 입력하세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String loginPw;

    @NotBlank(message = "비밀번호 확인을 입력하세요.")
    private String loginPwCheck;

    @NotBlank(message = "닉네임을 입력하세요.")
    private String nickname;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일 형식을 맞추어 입력하세요.")
    private String email;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "-를 포함한 핸드폰 번호를 형식에 맞추어 입력하세요.")
    private String phoneNumber;

}
