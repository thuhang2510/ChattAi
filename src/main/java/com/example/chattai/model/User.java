package com.example.chattai.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String gender;

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
