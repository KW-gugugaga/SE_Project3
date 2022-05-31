package conpanda9.shop.domain;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @NotNull
    private String loginId;

    @NotNull
    private String loginPw;

    private String nickname;
    private String email;
    private String phoneNumber;

    public User() {
    }

    public User(String loginId, String loginPw, String nickname, String email, String phoneNumber) {
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
