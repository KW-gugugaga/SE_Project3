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
        User user3 = new User("test3", "test!", "보경이", "bob@gmail.com", "010-1234-0000");
        User user4 = new User("test4", "test!", "예리밍", "yeye@gmail.com", "010-3423-2222");
        User user5 = new User("test5", "test!", "워노", "owon@gmail.com", "010-2222-2342");
        User user6 = new User("test6", "test!", "앵비니", "ang@gmail.com", "010-2222-1234");
        User user7 = new User("test7", "test!", "나야나", "nana@gmail.com", "010-2342-2222");
        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
        userRepository.save(user6);
        userRepository.save(user7);

        Seller store = new Seller("store", user);
        Seller store3 = new Seller("봉이상점", user3);
        Seller store4 = new Seller("멋쟁이", user4);
        Seller store5 = new Seller("동이필요해", user5);
        Seller store6 = new Seller("티끌모아티끌", user6);
        userRepository.saveStore(store);
        userRepository.saveStore(store3);
        userRepository.saveStore(store4);
        userRepository.saveStore(store5);
        userRepository.saveStore(store6);

        Notice notice1 = new Notice("notice1", "contents1", LocalDateTime.now(), LocalDateTime.now(), false);
        Notice notice2 = new Notice("notice2", "contents2", LocalDateTime.now(), LocalDateTime.now(), true);
        adminRepository.saveNotice(notice1);
        adminRepository.saveNotice(notice2);

        Category chicken = new Category("치킨", "/img/Category/fried-chicken.png");
        Category coffee = new Category("카페","/img/Category/coffee.png");
        Category bakery = new Category("베이커리/도넛/떡","/img/Category/bread.png");
        Category rice = new Category("한식","/img/Category/rice.png");
        Category burger = new Category("패스트푸드","/img/Category/burger.png");
        Category convi = new Category("편의점","/img/Category/24-hours.png");
        Category giftcard = new Category("상품권","/img/Category/gift-card.png");
        Category icecream = new Category("아이스크림/빙수","/img/Category/ice-cream.png");
        Category more = new Category("기타","/img/Category/more.png");

        itemRepository.saveCategory(chicken);
        itemRepository.saveCategory(coffee);
        itemRepository.saveCategory(bakery);
        itemRepository.saveCategory(rice);
        itemRepository.saveCategory(burger);
        itemRepository.saveCategory(convi);
        itemRepository.saveCategory(giftcard);
        itemRepository.saveCategory(icecream);
        itemRepository.saveCategory(more);

        Brand brand1 = new Brand("맘스터치", burger, "/img/");
        Brand brand2 = new Brand("맥도날드", chicken,"/img/");
        Brand brand3 = new Brand("스타벅스", coffee, "/img/cafe/starbucks.png");
        Brand brand4 = new Brand("이디야", coffee,"/img/cafe/ediya.png");
        Brand brand5 = new Brand("빽다방", coffee, "/img/cafe/bback.png");
        Brand brand6 = new Brand("앤젤리너스", coffee, "/img/cafe/angelinus.png");
        Brand brand7 = new Brand("공차", coffee, "/img/cafe/gongcha.png");
        Brand brand8 = new Brand("메가커피", coffee, "/img/cafe/mega.png");
        Brand brand9 = new Brand("탐앤탐스", coffee, "/img/cafe/tomntoms.jpg");
        Brand brand10 = new Brand("투썸플레이스", coffee, "/img/cafe/twosomeplace.png");
        Brand brand14 = new Brand("올리브영", more,"/img/");
        Brand brand15 = new Brand("신세계백화점", giftcard,"/img");
        itemRepository.saveBrand(brand1);
        itemRepository.saveBrand(brand2);
        itemRepository.saveBrand(brand3);
        itemRepository.saveBrand(brand4);
        itemRepository.saveBrand(brand5);
        itemRepository.saveBrand(brand6);
        itemRepository.saveBrand(brand7);
        itemRepository.saveBrand(brand8);
        itemRepository.saveBrand(brand9);
        itemRepository.saveBrand(brand10);
        itemRepository.saveBrand(brand14);
        itemRepository.saveBrand(brand15);


        Gifticon g1 = new Gifticon("아이스아메리카노", store, coffee, brand3, "/img/gifticon/37_fake.png", "/img/gifticon/37_real.jpg", "스벅 아아", 4000L, 2000L, LocalDate.of(2022, 6, 7),
                LocalDateTime.of(2022, 3, 1, 3, 3), LocalDateTime.of(2022, 3, 2, 3, 3));
        Gifticon g2 = new Gifticon("돌체라떼", store4, coffee, brand3, "/img/gifticon/38_fake.png","/img/gifticon/38_real.jpg", "스벅 돌체라떼", 4000L, 1000L, LocalDate.of(2022, 8, 3),
                LocalDateTime.of(2022, 3, 1, 3, 3), LocalDateTime.of(2022, 3, 3, 3, 3));

        Gifticon g3 = new Gifticon("에스프레소", store3, coffee, brand3, "/img/gifticon/39_fake.png","/img/gifticon/39_real.png", "스벅 에스프레소", 3000L, 2000L, LocalDate.of(2022, 6, 6),
                LocalDateTime.of(2022, 3, 1, 3, 3), LocalDateTime.of(2022, 3, 3, 3, 3));
        Gifticon g4 = new Gifticon("나이트로 콜드브루", store5, coffee, brand3, "/img/gifticon/40_fake.png","/img/gifticon/40_real.png", "스벅 나이트로 콜드브루", 5000L, 3000L, LocalDate.of(2022, 8, 5),
                LocalDateTime.of(2022, 3, 1, 3, 3), LocalDateTime.of(2022, 3, 4, 3, 3));


        Gifticon g5 = new Gifticon("망고패션후르츠블랜디드", store4, coffee, brand3, "/img/gifticon/41_fake.png", "/img/gifticon/41_real.png", "스벅 망고패션후르츠", 6000L, 3000L, LocalDate.of(2022, 6, 9),
                LocalDateTime.of(2022, 4, 1, 4, 4), LocalDateTime.of(2022, 4, 2, 4, 4));
        Gifticon g6 = new Gifticon("바닐라크림프라푸치노", store4, coffee, brand3, "/img/gifticon/42_fake.png", "/img/gifticon/42_real.jpg","스벅 바닐라크림프라푸치노", 6500L, 4000L, LocalDate.of(2022, 8, 7),
                LocalDateTime.of(2022, 4, 1, 4, 4), LocalDateTime.of(2022, 4, 3, 4, 4));

        Gifticon g7 = new Gifticon("바닐라크림콜드브루", store4, coffee, brand3, "/img/gifticon/43_fake.png", "/img/gifticon/43_real.jpg","스벅 바크콜", 5800L, 3500L, LocalDate.of(2022, 6, 12),
                LocalDateTime.of(2022, 4, 1, 4, 4), LocalDateTime.of(2022, 4, 3, 4, 4));
        Gifticon g8 = new Gifticon("딸기딜라이트요거트블랜디드", store4, coffee, brand3, "/img/gifticon/44_fake.png", "/img/gifticon/44_real.jpg","스벅 딸기요거트", 6800L, 5000L, LocalDate.of(2022, 8, 9),
                LocalDateTime.of(2022, 4, 1, 4, 4), LocalDateTime.of(2022, 4, 4, 4, 4));


        Gifticon g9 = new Gifticon("자바칩프라푸치노", store4, coffee, brand3, "/img/gifticon/45_fake.png","/img/gifticon/45_real.jpg", "스벅 자바칩", 6500L, 3200L, LocalDate.of(2022, 8, 10), LocalDateTime.of(2022, 5, 1, 5, 5), LocalDateTime.of(2022, 5, 2, 5, 5));
        Gifticon g10 = new Gifticon("자몽허니블랙티", store4, coffee, brand3, "/img/gifticon/46_fake.png","/img/gifticon/46_real.jpg","스벅 자허블", 5400L, 3000L, LocalDate.of(2022, 8, 11), LocalDateTime.of(2022, 5, 1, 5, 5), LocalDateTime.of(2022, 5, 3, 5, 5));

        Gifticon g11 = new Gifticon("쿨라임피지오", store4, coffee, brand3, "/img/gifticon/47_fake.png", "/img/gifticon/47_real.jpg","스벅 쿨라임피지오", 5600L, 5000L, LocalDate.of(2022, 8, 12),
                LocalDateTime.of(2022, 5, 1, 5, 5), LocalDateTime.of(2022, 5, 4, 5, 5));
        Gifticon g12 = new Gifticon("부드러운 생크림 카스테라 + 아이스아메리카노 2잔", store4, coffee, brand3, "/img/gifticon/48_fake.jpg", "/img/gifticon/48_real.jpg","스벅 세트", 50000L, 40000L, LocalDate.of(2022, 8, 13),
                LocalDateTime.of(2022, 5, 1, 5, 5), LocalDateTime.of(2022, 5, 5, 5, 5));
        Gifticon g13 = new Gifticon("스벅 3만원권", store4, coffee, brand3, "/img/gifticon/49_fake.jpg", "/img/gifticon/49_real.jpg","스벅 3만원권", 30000L, 15000L, LocalDate.of(2022, 8, 13),
                LocalDateTime.of(2022, 5, 1, 5, 5), LocalDateTime.of(2022, 5, 5, 5, 5));
        Gifticon g14 = new Gifticon("아이스아메리카노", store4, coffee, brand3, "/img/gifticon/50_fake.png", "/img/gifticon/50_real.jpg","스벅 아아", 4000L, 2000L, LocalDate.of(2022, 8, 13),
                LocalDateTime.of(2022, 5, 1, 5, 5), LocalDateTime.of(2022, 5, 5, 5, 5));
        Gifticon g15 = new Gifticon("아이스아메리카노", store4, coffee, brand3, "/img/gifticon/51_fake.png", "/img/gifticon/50_real.jpg","스벅 아아", 4000L, 1500L, LocalDate.of(2022, 8, 13),
                LocalDateTime.of(2022, 5, 1, 5, 5), LocalDateTime.of(2022, 5, 5, 5, 5));

        itemRepository.updateGifticonStateSold(g1);
        itemRepository.updateGifticonStateSold(g2);

        itemRepository.saveGifticon(g1); itemRepository.saveGifticon(g2);
        itemRepository.saveGifticon(g3); itemRepository.saveGifticon(g4);
        itemRepository.saveGifticon(g5); itemRepository.saveGifticon(g6);
        itemRepository.saveGifticon(g7); itemRepository.saveGifticon(g8);
        itemRepository.saveGifticon(g9); itemRepository.saveGifticon(g10);
        itemRepository.saveGifticon(g11); itemRepository.saveGifticon(g12);
        itemRepository.saveGifticon(g13); itemRepository.saveGifticon(g14);
        itemRepository.saveGifticon(g15);

        Sold sold = new Sold(g1, store4, user2, 2000L, LocalDateTime.of(2022, 3, 5, 5, 5));
        Sold sold2 = new Sold(g2, store4, user2, 2000L, LocalDateTime.of(2022, 3, 6, 5, 5));
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
        Question question2 = new Question("question2", "text2", user3, LocalDateTime.now(), LocalDateTime.now());
        Question question3 = new Question("question3", "text3", user4, LocalDateTime.now(), LocalDateTime.now());
        Question question4 = new Question("question4", "text4", user5, LocalDateTime.now(), LocalDateTime.now());
        question1.setAnswer("answer1");
        question2.setAnswer("answer2");
        itemRepository.saveQuestion(question1);
        itemRepository.saveQuestion(question2);
        itemRepository.saveQuestion(question3);
        itemRepository.saveQuestion(question4);

        Review review1 = new Review(user2, store4, "review1", 3);
        Review review2 = new Review(user3, store4, "review1", 5);
        Review review3 = new Review(user4, store4, "review 처음남겨요", 3);
        Review review4 = new Review(user5, store4, "감사합니다!! 개꿀", 3);
        Review review5 = new Review(user6, store4, "좋은 상품 싸게 샀어요", 3);
        itemRepository.saveReview(review1);
        itemRepository.saveReview(review2);
        itemRepository.saveReview(review3);
        itemRepository.saveReview(review4);
        itemRepository.saveReview(review5);

        Report report = new Report(user2, store4, "허위 매물이에요ㅡㅡ", ReportReason.FAKE);
        userRepository.saveReport(report);

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
