package conpanda9.shop.domain.soldcomparator;

import conpanda9.shop.domain.Sold;

import java.util.Comparator;

public class SoldDateComparator implements Comparator<Sold> {
    @Override
    public int compare(Sold o1, Sold o2) {
        if(o1.getSoldDate().isBefore(o2.getSoldDate())) {
            return 1;   // 더 최신순인 상품이 더 앞으로
        } else {
            return -1;
        }
    }
}
