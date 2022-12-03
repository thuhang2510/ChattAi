package com.example.chattai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    Integer id;
    String lastname;
    String firstname;
    String gender;
    String email;
}
