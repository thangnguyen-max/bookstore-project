package com.example.bookstore.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false,columnDefinition="VARCHAR(255) DEFAULT 'avatar.jpg'")
    private String avatar;

    @NotNull
    @Size(min=2)
    private String fullName;

    @NotNull
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotNull
    @Size(min=8, message="Password must be at least 8 characters")
    private String password;

    @NotNull
    @Size(min = 10, max = 10, message = "Phone number must be have 10 character")
    private String phone;

    @NotNull
    @NotEmpty(message="Address cannot be empty")
    private String address;


    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToOne(mappedBy = "user")
    private Cart cart;
}
