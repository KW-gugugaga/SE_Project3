package conpanda9.shop.domain;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String loginId;

    private String loginPw;

    private String nickname;

    private String email;

    private String phoneNumber;

    private Long point;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Alarm> alarmList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Question> questionList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Review> reviewList = new ArrayList<>();

    public User(String loginId, String loginPw, String nickname, String email, String phoneNumber) {
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.point = 0L;
    }
}
