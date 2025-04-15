package com.ecommerce.app.model.entity;

import com.ecommerce.app.utils.Role;
import com.ecommerce.app.utils.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long uid;


    String firstName;
    String lastName;
    String phone;

    @Column(unique = true, nullable = false)
    String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @JsonIgnore
    boolean isEnabled;
    @JsonIgnore
    String forgotPasswordToken;
    @JsonIgnore
    String verificationToken;
    @JsonIgnore
    boolean isLocked=false;

    @Enumerated(EnumType.STRING)
    private Role role;



    @Getter
    @CreatedDate
    private Long createdAt;

    @LastModifiedDate
    private Long updatedAt;

    private Status status = Status.ACTIVE;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Comment> comments = new ArrayList<>();

//
//    @PrePersist
//    public void generateUid() {
//        if (this.uid == null) {
//            this.uid = System.currentTimeMillis() % 1000;
//        }
//    }

    public User(String firstName, String lastName, String phone, String email, String username, String password, boolean isEnabled, String verificationToken, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
        this.verificationToken = verificationToken;
        this.role = role;
    }
}
