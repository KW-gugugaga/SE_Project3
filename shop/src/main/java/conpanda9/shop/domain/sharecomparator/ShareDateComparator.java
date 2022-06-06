package conpanda9.shop.domain.sharecomparator;

import conpanda9.shop.domain.Share;

import java.util.Comparator;

public class ShareDateComparator implements Comparator<Share> {
    @Override
    public int compare(Share o1, Share o2) {
        if(o1.getLastModifiedDate().isBefore(o2.getLastModifiedDate())) {
            return 1;   // 더 최신순인 상품이 더 앞으로
        } else {
            return -1;
        }
    }
}
