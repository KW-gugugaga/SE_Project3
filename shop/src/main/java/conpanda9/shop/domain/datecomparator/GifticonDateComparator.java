package conpanda9.shop.domain.datecomparator;

import conpanda9.shop.domain.Gifticon;

import java.util.Comparator;

public class GifticonDateComparator implements Comparator<Gifticon> {
    @Override
    public int compare(Gifticon o1, Gifticon o2) {
        if(o1.getLastModifiedDate().isBefore(o2.getLastModifiedDate())) {
            return 1;   // 더 최신순인 상품이 더 앞으로
        } else {
            return -1;
        }
    }
}
