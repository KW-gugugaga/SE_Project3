package conpanda9.shop.domain.datecomparator;

import conpanda9.shop.domain.Gifticon;

import java.util.Comparator;

public class GifticonPriceComparator implements Comparator<Gifticon> {
    @Override
    public int compare(Gifticon o1, Gifticon o2) {
        if(o1.getSellingPrice() > o2.getSellingPrice()) {
            return 1;
        } else {
            return -1;
        }
    }
}
