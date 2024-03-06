package com.swiggy.authenticator.entities;

import com.swiggy.authenticator.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "admins")
@Data
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    public Admin(String username, String password) {
        this.user = new User(username, password, UserRole.ADMIN);
    }
}
