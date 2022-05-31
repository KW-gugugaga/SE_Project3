package conpanda9.shop.service;

import conpanda9.shop.DTO.LoginDTO;
import conpanda9.shop.domain.User;
import conpanda9.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

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
}
