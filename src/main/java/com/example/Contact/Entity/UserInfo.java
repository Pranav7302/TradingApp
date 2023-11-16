package com.example.Contact.Entity;

import com.example.Contact.Constants.Constants;
import com.example.Contact.ExceptionHandler.InvalidPasswordException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = Constants.Username_Constraint)
    private String userName;

    @NotBlank(message = Constants.Password_Not_Blank)
    private String password;

    @Column(unique = true)
    @Email(message = Constants.Email_Constraint)
    private String email;

    public void setPassword(String password) {
        if (!isValidPassword(password)) {
            throw new InvalidPasswordException(Constants.Password_Constraint);
        }
        this.password = password;
    }
    private String roles = "ROLE_USER";

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Watchlist> watchlists = new ArrayList<>();

    @OneToMany(mappedBy = "userOrdersId", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "userTradeId", cascade = CascadeType.ALL)
    private List<Trade> trade = new ArrayList<>();
    private boolean isValidPassword(String password) {
        String pattern = "^(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return password.matches(pattern);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(id, userInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
