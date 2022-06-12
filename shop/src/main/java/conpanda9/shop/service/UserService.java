package conpanda9.shop.service;

import conpanda9.shop.DTO.JoinDTO;
import conpanda9.shop.DTO.LoginDTO;
import conpanda9.shop.DTO.MyInfoEditDTO;
import conpanda9.shop.DTO.QuestionDTO;
import conpanda9.shop.domain.*;
import conpanda9.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public User findUser(Long id) {
        return userRepository.find(id);
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

    //회원정보 수정 시 중복 확인 메서드
    public void existCheck(MyInfoEditDTO editDTO, BindingResult bindingResult) {
        if(!userRepository.findOneById(editDTO.getLoginId()).isEmpty()) {
            bindingResult.addError(new FieldError("joinDTO", "loginId", editDTO.getLoginId(), false, null, null,  "중복 아이디가 존재합니다."));
        }
        if(!userRepository.findOneByNickname(editDTO.getNickname()).isEmpty()) {
            bindingResult.addError(new FieldError("joinDTO", "nickname", editDTO.getNickname(), false, null, null, "중복 닉네임이 존재합니다."));
        }
        if(!userRepository.findOneByEmail(editDTO.getEmail()).isEmpty()) {
            bindingResult.addError(new FieldError("joinDTO", "email", editDTO.getPhoneNumber(), false, null, null, "중복 이메일이 존재합니다."));
        }
    }

    public Optional<Seller> existCheck(String name) {
        return userRepository.findAllStore().stream().filter(s -> s.getName().equals(name)).findAny();
    }

    public void editUser(Long id, MyInfoEditDTO editDTO) {
        userRepository.editUser(id, editDTO);
    }

    public void editPw(Long id, String newPw) {
        userRepository.editPw(id, newPw);
    }

    public List<Question> findQuestionByUser(Long id) {
        return userRepository.findQuestionByUser(id);
    }

    public void saveQuestion(Question question) {
        userRepository.saveQuestion(question);
    }

    public Question findQuestion(Long id) {
        return userRepository.findQuestion(id);
    }

    public void editQuestion(Long id, QuestionDTO questionDTO) {
        userRepository.editQuestion(id, questionDTO);
    }

    public void deleteQuestion(Long id) {
        userRepository.deleteQuestion(id);
    }

    public void saveStore(Seller store) {
        userRepository.saveStore(store);
    }

    public Optional<Seller> findStore(Long id) {
        return userRepository.findStore(id);
    }

    public Optional<Seller> findOtherStore(Long id) {
        return userRepository.findOtherStore(id);
    }

    public List<Sold> findSolds(Long id) {
        return userRepository.findSolds(id);
    }

    public List<Gifticon> findSellings(Long id) {
        return userRepository.findSellings(id);
    }

    public Long getTotalSellPrice(Long id) {
        Long total = 0L;
        List<Sold> solds = userRepository.findSolds(id);
        for (Sold sold : solds) {
            total += sold.getSoldPrice();
        }
        return total;
    }

    public List<Sold> findBuys(Long id) {
        return userRepository.findBuys(id);
    }


    public List<Review> findReviews(Long id) {
        return userRepository.findReviews(id);
    }

    public Optional<User> findId(String nickname, String email) {
        return userRepository.findId(nickname, email);
    }

    public Optional<User> findPw(String loginId, String email) {
        return userRepository.findPw(loginId, email);
    }

    public Double getStoreStarRate(Long id) {
        List<Review> reviewList = userRepository.findAllReview().stream().filter(r -> r.getSeller().getId().equals(id)).collect(Collectors.toList());
        Integer totalStarRate = 0;
        for (Review review : reviewList) {
            totalStarRate += review.getStar();
        }
        return (double) totalStarRate / (double) reviewList.size();
    }

    public void saveReport(Report report) {
        userRepository.saveReport(report);
    }
}
