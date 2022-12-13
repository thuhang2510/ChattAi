package com.example.chattai.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tbl_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstname;
    private String lastname;

    @NotBlank(message = "Email cannot blank")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password cannot blank")
    private String password;

    private String gender;
    private String resetPasswordToken;

    public User(String firstname, String lastname, String email, String password, String gender){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    public User(Integer id, String firstname, String lastname, String email, String password, String gender){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }
}
