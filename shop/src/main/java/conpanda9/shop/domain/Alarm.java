package conpanda9.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Alarm {

    @Id @GeneratedValue
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime alarmDate;   // 알람 전송 시간

    private String title; //알림 제목

    private String text;   // 알람 내용

    private boolean checked;   // 알람 확인 여부

    public boolean isChecked() {
        return checked;
    }

    public Alarm(User user, LocalDateTime alarmDate,String title, String text, boolean checked) {
        this.user = user;
        this.alarmDate = alarmDate;
        this.title = title;
        this.text = text;
        this.checked = checked;
        user.getAlarmList().add(this);
    }
}
