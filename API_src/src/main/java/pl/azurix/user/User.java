package pl.azurix.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Table(name = "users")
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String login;

    private int authLvl;
    //0- root
    //1- user

    @JsonIgnore
    private String password;

    public User() {
    }

    public User(String email, String login, String password, int authLvl) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.authLvl=authLvl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getAuthLvl() {
        return authLvl;
    }

    public void setAuthLvl(int authLvl) {
        this.authLvl = authLvl;
    }
}
