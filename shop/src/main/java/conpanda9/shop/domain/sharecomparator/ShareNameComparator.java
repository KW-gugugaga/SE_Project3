package conpanda9.shop.domain.sharecomparator;

import conpanda9.shop.domain.Share;

import java.util.Comparator;

public class ShareNameComparator implements Comparator<Share> {
    @Override
    public int compare(Share o1, Share o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
