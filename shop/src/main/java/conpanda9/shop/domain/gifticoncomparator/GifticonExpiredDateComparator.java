package conpanda9.shop.domain.gifticoncomparator;

import conpanda9.shop.domain.Gifticon;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Comparator;

public class GifticonExpiredDateComparator implements Comparator<Gifticon> {
    @Override
    public int compare(Gifticon o1, Gifticon o2) {
        Duration o1Duration = Duration.between(o1.getExpireDate().atStartOfDay(), LocalDate.now().atStartOfDay());
        Duration o2Duration = Duration.between(o2.getExpireDate().atStartOfDay(), LocalDate.now().atStartOfDay());
        if(o1Duration.getSeconds() < o2Duration.getSeconds()) {
            return 1;
        } else {
            return -1;
        }
    }
}
