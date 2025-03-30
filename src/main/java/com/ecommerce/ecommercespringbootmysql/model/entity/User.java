package com.ecommerce.ecommercespringbootmysql.model.entity;

import com.ecommerce.ecommercespringbootmysql.utils.Role;
import com.ecommerce.ecommercespringbootmysql.utils.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
