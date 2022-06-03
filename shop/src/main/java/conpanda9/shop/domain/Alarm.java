package conpanda9.shop.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Alarm {

    @Id @GeneratedValue
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime alarmDate;   // 알람 전송 시간

    private String text;   // 알람 내용

    private boolean checked;   // 알람 확인 여부

    public boolean isChecked() {
        return checked;
    }
}
