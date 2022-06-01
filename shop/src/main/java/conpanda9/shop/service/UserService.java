package conpanda9.shop.service;

import conpanda9.shop.DTO.JoinDTO;
import conpanda9.shop.DTO.LoginDTO;
import conpanda9.shop.domain.User;
import conpanda9.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public User login(LoginDTO loginDTO) {
        log.info("UserService Login");
        Optional<User> oneById = userRepository.findOneById(loginDTO.getLoginId());
        if(oneById.isEmpty()) {   // 아이디 일지 회원이 없을 경우
            log.info("Optional Null");
            return null;
        }
        else {   // 아이디 일치 회원이 존재할 경우 비밀번호 일치 회원이 있을때 회원 반환, 없으면 null 반환
            return oneById.filter(m -> m.getLoginPw().equals(loginDTO.getLoginPw())).orElse(null);
        }
    }

    //회원가입 시 중복 확인 메서드
    public void existsCheck(JoinDTO joinDTO, BindingResult bindingResult){
        log.info("userService existCheck");
        if(!userRepository.findOneById(joinDTO.getLoginId()).isEmpty()) {
            bindingResult.addError(new FieldError("joinDTO", "loginId", joinDTO.getLoginId(), false, null, null,  "중복 아이디가 존재합니다."));
        }
        if(!userRepository.findOneByNickname(joinDTO.getNickname()).isEmpty()) {
            bindingResult.addError(new FieldError("joinDTO", "nickname", joinDTO.getNickname(), false, null, null, "중복 닉네임이 존재합니다."));
        }
        if(!userRepository.findOneByEmail(joinDTO.getEmail()).isEmpty()) {
            bindingResult.addError(new FieldError("joinDTO", "email", joinDTO.getPhoneNumber(), false, null, null, "중복 이메일이 존재합니다."));
        }
    }
}
