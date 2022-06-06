package conpanda9.shop.domain.sharecomparator;

import conpanda9.shop.domain.Share;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Comparator;

public class ShareExpiredDateComparator implements Comparator<Share> {
    @Override
    public int compare(Share o1, Share o2) {
        Duration o1Duration = Duration.between(o1.getExpireDate().atStartOfDay(), LocalDate.now().atStartOfDay());
        Duration o2Duration = Duration.between(o2.getExpireDate().atStartOfDay(), LocalDate.now().atStartOfDay());
        if(o1Duration.getSeconds() < o2Duration.getSeconds()) {
            return 1;
        } else {
            return -1;
        }
    }
}
