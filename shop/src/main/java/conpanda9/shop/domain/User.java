package conpanda9.shop.domain;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;

@Data
@Entity
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String loginId;

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
