package conpanda9.shop;

import conpanda9.shop.domain.*;
import conpanda9.shop.repository.AdminRepository;
import conpanda9.shop.repository.ItemRepository;
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

    @PostConstruct
    public void dataInit() {

        User admin = new User("admin", "admin!", "admin", "admin@gmail.com", "010-1111-1111");
        userRepository.save(admin);

        User user = new User("test", "test!", "nickname", "email@gmail.com", "010-0000-0000");
        User user2 = new User("test2", "test!", "nickname2", "email2@gmail.com", "010-2222-2222");
        userRepository.save(user);
        userRepository.save(user2);

        Seller store = new Seller("store", user);
        userRepository.saveStore(store);

        Notice notice1 = new Notice("notice1", "contents1", LocalDateTime.now(), LocalDateTime.now(), false);
        Notice notice2 = new Notice("notice2", "contents2", LocalDateTime.now(), LocalDateTime.now(), true);
        adminRepository.saveNotice(notice1);
        adminRepository.saveNotice(notice2);

        Category cate1 = new Category("프랜차이즈");
        Category cate2 = new Category("음료");
        Category cate3 = new Category("생활");
        Category cate4 = new Category("cate4");
        Category cate5 = new Category("cate5");
        Category cate6 = new Category("cate6");
        Category cate7 = new Category("cate7");
        Category cate8 = new Category("cate8");
        Category cate9 = new Category("cate9");

        itemRepository.saveCategory(cate1);
        itemRepository.saveCategory(cate2);
        itemRepository.saveCategory(cate3);
        itemRepository.saveCategory(cate4);
        itemRepository.saveCategory(cate5);
        itemRepository.saveCategory(cate6);
        itemRepository.saveCategory(cate7);
        itemRepository.saveCategory(cate8);
        itemRepository.saveCategory(cate9);

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
        Gifticon g1 = new Gifticon("싸이버거", store, cate1, brand1, null, "des", 4000L, 2000L, LocalDate.of(2022, 6, 7),
                LocalDateTime.of(2022, 3, 1, 3, 3), LocalDateTime.of(2022, 3, 2, 3, 3));
        Gifticon g2 = new Gifticon("할라피뇨", store, cate1, brand1, null, "des", 4000L, 1000L, LocalDate.of(2022, 8, 3),
                LocalDateTime.of(2022, 3, 1, 3, 3), LocalDateTime.of(2022, 3, 3, 3, 3));
        //맥날
        Gifticon g3 = new Gifticon("더블불고기", store, cate1, brand2, null, "des", 3000L, 2000L, LocalDate.of(2022, 6, 6),
                LocalDateTime.of(2022, 3, 1, 3, 3), LocalDateTime.of(2022, 3, 3, 3, 3));
        Gifticon g4 = new Gifticon("상하이", store, cate1, brand2, null, "des", 3000L, 1000L, LocalDate.of(2022, 8, 5),
                LocalDateTime.of(2022, 3, 1, 3, 3), LocalDateTime.of(2022, 3, 4, 3, 3));

        /*
        음료
         */
        //스벅
        Gifticon g5 = new Gifticon("자허블", store, cate2, brand3, null, "des", 4000L, 2000L, LocalDate.of(2022, 6, 9),
                LocalDateTime.of(2022, 4, 1, 4, 4), LocalDateTime.of(2022, 4, 2, 4, 4));
        Gifticon g6 = new Gifticon("콜드브루", store, cate2, brand3, null, "des", 4000L, 1000L, LocalDate.of(2022, 8, 7),
                LocalDateTime.of(2022, 4, 1, 4, 4), LocalDateTime.of(2022, 4, 3, 4, 4));
        //이디야
        Gifticon g7 = new Gifticon("꿀복숭아플랫치노", store, cate2, brand4, null, "des", 4000L, 2000L, LocalDate.of(2022, 6, 12),
                LocalDateTime.of(2022, 4, 1, 4, 4), LocalDateTime.of(2022, 4, 3, 4, 4));
        Gifticon g8 = new Gifticon("아이스아메리카노", store, cate2, brand4, null, "des", 4000L, 1000L, LocalDate.of(2022, 8, 9),
                LocalDateTime.of(2022, 4, 1, 4, 4), LocalDateTime.of(2022, 4, 4, 4, 4));

        /*
        생활
         */
        //올영
        Gifticon g9 = new Gifticon("랑방 향수", store, cate3, brand5, null, "des", 4000L, 2000L, LocalDate.of(2022, 8, 10),
                LocalDateTime.of(2022, 5, 1, 5, 5), LocalDateTime.of(2022, 5, 2, 5, 5));
        Gifticon g10 = new Gifticon("30,000원권", store, cate3, brand5, null, "des", 4000L, 1000L, LocalDate.of(2022, 8, 11),
                LocalDateTime.of(2022, 5, 1, 5, 5), LocalDateTime.of(2022, 5, 3, 5, 5));
        //신세계
        Gifticon g11 = new Gifticon("100,000원권", store, cate3, brand6, null, "des", 3000L, 2000L, LocalDate.of(2022, 8, 12),
                LocalDateTime.of(2022, 5, 1, 5, 5), LocalDateTime.of(2022, 5, 4, 5, 5));
        Gifticon g12 = new Gifticon("50,000원권", store, cate3, brand6, null, "des", 3000L, 1000L, LocalDate.of(2022, 8, 13),
                LocalDateTime.of(2022, 5, 1, 5, 5), LocalDateTime.of(2022, 5, 5, 5, 5));
        itemRepository.updateGifticonStateSold(g1);
        itemRepository.updateGifticonStateSold(g3);

        itemRepository.saveGifticon(g1); itemRepository.saveGifticon(g2);
        itemRepository.saveGifticon(g3); itemRepository.saveGifticon(g4);
        itemRepository.saveGifticon(g5); itemRepository.saveGifticon(g6);
        itemRepository.saveGifticon(g7); itemRepository.saveGifticon(g8);
        itemRepository.saveGifticon(g9); itemRepository.saveGifticon(g10);
        itemRepository.saveGifticon(g11); itemRepository.saveGifticon(g12);

        Sold sold = new Sold(g1, store, user2, 2000L, LocalDateTime.of(2022, 5, 5, 5, 5));
        Sold sold2 = new Sold(g3, store, user2, 2000L, LocalDateTime.of(2022, 5, 6, 5, 5));
        itemRepository.saveSold(sold);
        itemRepository.saveSold(sold2);

        Share share1 = new Share("맘스터치 세트", brand1, store, null, "share1", 10000L, LocalDateTime.of(2022, 5, 4, 6, 6), LocalDateTime.of(2022, 6, 1, 6, 6), LocalDate.of(2022, 8, 8));
        Share share2 = new Share("스타벅스 세트", brand3, store, null, "share2", 10000L, LocalDateTime.of(2022, 5, 3, 6, 6), LocalDateTime.of(2022, 6, 2, 6, 6), LocalDate.of(2022, 7, 8));
        Share share3 = new Share("올리브영 세트", brand5, store, null, "share3", 10000L, LocalDateTime.of(2022, 5, 3, 6, 6), LocalDateTime.of(2022, 6, 3, 6, 6), LocalDate.of(2022, 7, 8));
        Share share4 = new Share("이디야 세트", brand4, store, null, "share4", 10000L, LocalDateTime.of(2022, 5, 3, 6, 6), LocalDateTime.of(2022, 6, 4, 6, 6), LocalDate.of(2022, 7, 8));
        share1.setState(ShareState.Shared);
        share2.setState(ShareState.Shared);
        itemRepository.saveShare(share1);
        itemRepository.saveShare(share2);
        itemRepository.saveShare(share3);
        itemRepository.saveShare(share4);

        Shared shared1 = new Shared(user2, store, share1);
        Shared shared2 = new Shared(user2, store, share2);
        itemRepository.saveShared(shared1);
        itemRepository.saveShared(shared2);

        Question question1 = new Question("question1", "text1", user, LocalDateTime.now(), LocalDateTime.now());
        Question question2 = new Question("question2", "text2", user, LocalDateTime.now(), LocalDateTime.now());
        Question question3 = new Question("question3", "text3", user, LocalDateTime.now(), LocalDateTime.now());
        Question question4 = new Question("question4", "text4", user, LocalDateTime.now(), LocalDateTime.now());
        question1.setAnswer("answer1");
        question2.setAnswer("answer2");
        itemRepository.saveQuestion(question1);
        itemRepository.saveQuestion(question2);
        itemRepository.saveQuestion(question3);
        itemRepository.saveQuestion(question4);

        Review review = new Review(user2, store, "review1", 1);
        itemRepository.saveReview(review);

        Alarm alarm1 = new Alarm(user, LocalDateTime.now(), "당신의 기프티콘을 판매해보세요!","기프티콘을 판매하려면, 상점을 먼저 개설하세요!", false);
        Alarm alarm2 = new Alarm(user, LocalDateTime.now(), "당신의 기프티콘이 판매되었습니다!","첫 판매를 축하드려요!!", false);
        Alarm alarm3 = new Alarm(user, LocalDateTime.now(), "환영합니다!", "콘판다에서 여러분의 기프티콘을 팔아보세요!!",true);
        Alarm alarm4 = new Alarm(user, LocalDateTime.now(), "상점을 개설해보세요!","상점을 개설하려면, 내 상점 페이지로 이동하세요!", true);
        userRepository.saveAlarm(alarm1);
        userRepository.saveAlarm(alarm2);
        userRepository.saveAlarm(alarm3);
        userRepository.saveAlarm(alarm4);

        log.info("test data init");
    }
}
