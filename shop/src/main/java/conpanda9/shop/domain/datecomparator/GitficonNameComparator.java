package conpanda9.shop.domain.datecomparator;

import conpanda9.shop.domain.Gifticon;

import java.util.Comparator;

public class GitficonNameComparator implements Comparator<Gifticon> {
    @Override
    public int compare(Gifticon o1, Gifticon o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
