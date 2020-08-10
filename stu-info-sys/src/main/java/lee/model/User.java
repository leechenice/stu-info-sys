package lee.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class User {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private Date createTime;
}
