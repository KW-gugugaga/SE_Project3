package conpanda9.shop;

import conpanda9.shop.domain.*;
import conpanda9.shop.repository.AdminRepository;
import conpanda9.shop.repository.ItemRepository;
import conpanda9.shop.repository.QuestionRepository;
import conpanda9.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class TestDataInit {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final ItemRepository itemRepository;
    private final QuestionRepository questionRepository;

    @PostConstruct
    public void dataInit() {

        User admin = new User("admin", "admin!", "admin", "admin@gmail.com", "010-1111-1111");
        userRepository.save(admin);
        User user = new User("test", "test!", "nickname", "email@gmail.com", "010-0000-0000");
        userRepository.save(user);
        log.info("test data init");

        Notice notice1 = new Notice("notice1", "contents1", LocalDateTime.now(), LocalDateTime.now(), false);
        Notice notice2 = new Notice("notice2", "contents2", LocalDateTime.now(), LocalDateTime.now(), true);
        adminRepository.saveNotice(notice1);
        adminRepository.saveNotice(notice2);

        Category cate1 = new Category("프랜차이즈");
        Category cate2 = new Category("음료");
        Category cate3 = new Category("생활");
        itemRepository.saveCategory(cate1);
        itemRepository.saveCategory(cate2);
        itemRepository.saveCategory(cate3);

        Brand brand1 = new Brand("맘스터치", cate1);
        Brand brand2 = new Brand("맥도날드", cate1);
        Brand brand3 = new Brand("스타벅스", cate2);
        Brand brand4 = new Brand("이디야", cate2);
        Brand brand5 = new Brand("올리브영", cate3);
        Brand brand6 = new Brand("신세계백화점", cate3);

        itemRepository.saveBrand(brand1);
        itemRepository.saveBrand(brand2);
        itemRepository.saveBrand(brand3);
        itemRepository.saveBrand(brand4);
        itemRepository.saveBrand(brand5);
        itemRepository.saveBrand(brand6);

        /*
        프랜차이즈
         */
        //맘터
        Gifticon g1 = new Gifticon("싸이버거", user, cate1, brand1, null, "des", 3000L, 2000L, LocalDate.of(2022, 8, 2),
                LocalDateTime.now(), LocalDateTime.now());
        Gifticon g2 = new Gifticon("할라피뇨", user, cate1, brand1, null, "des", 3000L, 2000L, LocalDate.of(2022, 8, 2),
                LocalDateTime.now(), LocalDateTime.now());
        //맥날
        Gifticon g3 = new Gifticon("더블불고기", user, cate1, brand2, null, "des", 3000L, 2000L, LocalDate.of(2022, 8, 2),
                LocalDateTime.now(), LocalDateTime.now());
        Gifticon g4 = new Gifticon("상하이", user, cate1, brand2, null, "des", 3000L, 2000L, LocalDate.of(2022, 8, 2),
                LocalDateTime.now(), LocalDateTime.now());

        /*
        음료
         */
        //스벅
        Gifticon g5 = new Gifticon("자허블", user, cate2, brand3, null, "des", 3000L, 2000L, LocalDate.of(2022, 8, 2),
                LocalDateTime.now(), LocalDateTime.now());
        Gifticon g6 = new Gifticon("콜드브루", user, cate2, brand3, null, "des", 3000L, 2000L, LocalDate.of(2022, 8, 2),
                LocalDateTime.now(), LocalDateTime.now());
        //이디야
        Gifticon g7 = new Gifticon("꿀복숭아플랫치노", user, cate2, brand4, null, "des", 3000L, 2000L, LocalDate.of(2022, 8, 2),
                LocalDateTime.now(), LocalDateTime.now());
        Gifticon g8 = new Gifticon("아이스아메리카노", user, cate2, brand4, null, "des", 3000L, 2000L, LocalDate.of(2022, 8, 2),
                LocalDateTime.now(), LocalDateTime.now());

        /*
        생활
         */
        //올영
        Gifticon g9 = new Gifticon("랑방 향수", user, cate3, brand5, null, "des", 3000L, 2000L, LocalDate.of(2022, 8, 2),
                LocalDateTime.now(), LocalDateTime.now());
        Gifticon g10 = new Gifticon("30,000원권", user, cate3, brand5, null, "des", 3000L, 2000L, LocalDate.of(2022, 8, 2),
                LocalDateTime.now(), LocalDateTime.now());
        //신세계
        Gifticon g11 = new Gifticon("100,000원권", user, cate3, brand6, null, "des", 3000L, 2000L, LocalDate.of(2022, 8, 2),
                LocalDateTime.now(), LocalDateTime.now());
        Gifticon g12 = new Gifticon("50,000원권", user, cate3, brand6, null, "des", 3000L, 2000L, LocalDate.of(2022, 8, 2),
                LocalDateTime.now(), LocalDateTime.now());

        itemRepository.saveGifticon(g1); itemRepository.saveGifticon(g2);
        itemRepository.saveGifticon(g3); itemRepository.saveGifticon(g4);
        itemRepository.saveGifticon(g5); itemRepository.saveGifticon(g6);
        itemRepository.saveGifticon(g7); itemRepository.saveGifticon(g8);
        itemRepository.saveGifticon(g9); itemRepository.saveGifticon(g10);
        itemRepository.saveGifticon(g11); itemRepository.saveGifticon(g12);


        Question question1 = new Question("question1","text1",user);
        Question question2 = new Question("question2","text2",user);

        questionRepository.saveQuestion(question1);
        questionRepository.saveQuestion(question2);

    }
}
