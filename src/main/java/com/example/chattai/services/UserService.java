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
}
