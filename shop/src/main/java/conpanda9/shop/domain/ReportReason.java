package conpanda9.shop.domain;

public enum ReportReason {
    DUPLICATE("중복 상품"), FAKE("허위 매물"), ABUSE("부적절한 상품"), EXTRA("기타");

    ReportReason(String value) {
    }
}
