package com.example.chattai.repositories;

import com.example.chattai.model.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    User getUserByEmailAndPassword(@Email String email, String password);
    User getUserByEmail(@Email String email);
}