package com.example.chattai.services;

import com.example.chattai.model.User;
import com.example.chattai.repositories.UserRepo;
import com.example.chattai.respone.BasicRespone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public BasicRespone<User> addUser(User userInfo) {
        try
        {
            User user = userRepo.save(userInfo);

            if(user == null)
                return new BasicRespone<User>("Add user failed", -1, null);

            return new BasicRespone<User>("Add user success", 0, user);
        }
        catch (RuntimeException ex)
        {
            return new BasicRespone<User>("Exception Occured : " + ex.getMessage(), -1, null);
        }
    }

    public BasicRespone<User> findByEmailAndPassword(String email, String password) {
        try
        {
            User user = userRepo.getUserByEmailAndPassword(email, password);
            if(user == null)
                return new BasicRespone<User>("User not exist", -1, null);

            return new BasicRespone<User>("User exist", 0, user);
        }
        catch (RuntimeException ex)
        {
            return new BasicRespone<User>("Exception Occured : " + ex.getMessage(), -1, null);
        }
    }

    public BasicRespone<User> signUp(User userInfo){
        try{
            User user = userRepo.getUserByEmailAndPassword(userInfo.getEmail(), userInfo.getPassword());

            if(user != null)
                return new BasicRespone<>("User exist", -1, null);

            return addUser(userInfo);
        }
        catch (RuntimeException ex)
        {
            return new BasicRespone<>("Exception Occured : " + ex.getMessage(), -1, null);
        }
    }

    public BasicRespone<User> updateResetPassword(String token, String email){
        try{
            User user = userRepo.getUserByEmail(email);

            if(user == null)
                return new BasicRespone<User>("email not exist", -1, null);

            user.setResetPasswordToken(token);

            user = userRepo.save(user);
            if(user == null)
                return new BasicRespone<>("Update reset password not success", -1, null);
            return new BasicRespone<>("Update reset password success", 0, user);
        }
        catch(RuntimeException ex)
        {
            return new BasicRespone<>("Exception Occured : " + ex.getMessage(), -1, null);
        }
    }
}
