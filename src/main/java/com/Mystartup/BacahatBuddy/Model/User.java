package com.Mystartup.BacahatBuddy.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String username;
    private String password;
    private String gender;
    private LocalDate dateOfBirth;
    private String email;
    private String nationality;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore   // ✅ Prevent infinite recursion
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore   // ✅ Prevent infinite recursion
    private List<Order> orders = new ArrayList<>();

    // getters & setters...
}