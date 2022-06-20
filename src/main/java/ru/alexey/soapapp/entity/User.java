package ru.alexey.soapapp.entity;
/* 
17.06.2022: Alexey created this file inside the package: ru.alexey.soapapp.entity 
*/

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "LOGIN", nullable = false)
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = @JoinColumn(
                    name = "USER_LOGIN",
                    nullable = false),
            inverseJoinColumns = @JoinColumn(
                    name = "ROLE_ID",
                    nullable = false)
    )
    private List<Role> roles;

    public User(String name, String password, List<Role> roles) {
        this.login = name;
        this.password = password;
        this.roles = roles;
    }

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
