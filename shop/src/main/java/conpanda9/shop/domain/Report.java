package conpanda9.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class Report {

    @Id @GeneratedValue
    @Column(name = "report_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Seller seller;

    @Enumerated(EnumType.STRING)
    private ReportReason reportReason;

    private boolean isCompleted;

    private LocalDateTime reportDate;

    private String content;

    public Report(User user, Seller seller, String content, ReportReason reportReason) {
        this.user = user;
        this.seller = seller;
        this.content = content;
        this.reportReason = reportReason;
        this.isCompleted = false;
        this.reportDate = LocalDateTime.now();
    }
}
