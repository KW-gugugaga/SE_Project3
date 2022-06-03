package conpanda9.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Notice {

    @Id @GeneratedValue
    @Column(name = "notice_id")
    private Long id;

    private String title;
    private String contents;
    private LocalDateTime uploadDate;   // 업로드 날짜
    private LocalDateTime lastModifiedDate;   // 마지막 수정 날짜
    private Long count;   // 조회수
    private boolean important;   // 중요 공지

    public boolean isImportant() {
        return important;
    }

    public Notice(String title, String contents, LocalDateTime uploadDate, LocalDateTime lastModifiedDate, boolean important) {
        this.title = title;
        this.contents = contents;
        this.uploadDate = uploadDate;
        this.lastModifiedDate = lastModifiedDate;
        this.important = important;
        this.count = 0L;
    }
}
