package com.example.chattai.controllers;

import com.example.chattai.dto.UserDTO;
import com.example.chattai.model.User;
import com.example.chattai.request.LoginRequest;
import com.example.chattai.respone.BasicRespone;
import com.example.chattai.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public BasicRespone<String> getToken(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors())
            return new BasicRespone<String>(bindingResult.getAllErrors().get(0).getDefaultMessage(), -1, null);

        try {
            BasicRespone<User> userBasicRespone = userService.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
            User user = userBasicRespone.getData();

            if (user == null)
                return new BasicRespone<String>("User not exist", -1, null);

            httpSession.setAttribute("user", new UserDTO(user.getId(), user.getFirstname(), user.getLastname(), user.getGender(), user.getEmail()));
            return new BasicRespone<String>("Get token success", 0, httpSession.getId());
        } catch (RuntimeException ex) {
            return new BasicRespone<String>(ex.getMessage(), -1, null);
        }
    }

    @PostMapping("/signup")
    public BasicRespone<User> signUp(@Valid @RequestBody User userInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new BasicRespone<>("Invalid User Info", -1, null);

        return userService.signUp(userInfo);
    }
}
