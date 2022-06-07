package conpanda9.shop.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PwEditDTO {
    
    @NotBlank(message = "새로운 비밀번호를 입력하세요")
    private String newPw;
    
    @NotBlank(message = "새로운 비밀번호 확인을 입력하세요")
    private String newPwCheck;
    
    @NotBlank(message = "기존 비밀번호를 입력하세요")
    private String originalPwCheck;
}
