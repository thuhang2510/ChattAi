package com.example.chattai.services;

import com.example.chattai.model.User;
import com.example.chattai.repositories.UserRepo;
import com.example.chattai.respone.BasicRespone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class UserServicesTest {

    @TestConfiguration
    public static class TestUserServiceConfiguration{
        @Bean
        UserService userService(){
            return new UserService();
        }
    }

    @MockBean
    UserRepo userRepo;

    @Autowired
    private UserService userService;

    private User user;

    @Before
    public void init(){
        user = new User(1, "nguyen", "mi", "damthuhang4@gmail.com", "thuhang123", "nu");
    }

    @Test
    public void givenValidUserWhenAddUserThenAddUserSuccess(){
        given(userRepo.getUserByEmail(user.getEmail())).willReturn(null);
        given(userRepo.save(user)).willReturn(user);

        BasicRespone<User>  userBasicRespone = userService.addUser(user);
        assertEquals(0, userBasicRespone.getCode());
        assertEquals("Add user success", userBasicRespone.getMessage());
        assertEquals(user, userBasicRespone.getData());
    }

    @Test
    public void givenDuplicatedEmailWhenAddUserThenAddUserFail() {
        given(userRepo.getUserByEmail(user.getEmail())).willReturn(user);
        given(userRepo.save(user)).willReturn(user);

        BasicRespone<User>  userBasicRespone = userService.addUser(user);
        assertEquals(-1, userBasicRespone.getCode());
        assertEquals("Email exist", userBasicRespone.getMessage());
        assertNull(userBasicRespone.getData());
    }

    @Test
    public void givenSaveUseInDataFailWhenAddUserThenAddUserFail(){
        given(userRepo.getUserByEmail(user.getEmail())).willReturn(null);
        given(userRepo.save(user)).willReturn(null);

        BasicRespone<User>  userBasicRespone = userService.addUser(user);
        assertEquals(-1, userBasicRespone.getCode());
        assertEquals("Add user failed", userBasicRespone.getMessage());
        assertNull(userBasicRespone.getData());
    }

    @Test
    public void givenValidInputWhenFindByEmailAndPasswordThenFindUserSuccess(){
        String email = user.getEmail();
        String password = user.getPassword();

        given(userRepo.getUserByEmailAndPassword(email, password)).willReturn(user);

        BasicRespone<User>  userBasicRespone = userService.findByEmailAndPassword(email, password);
        assertEquals(0, userBasicRespone.getCode());
        assertEquals("User exist", userBasicRespone.getMessage());
        assertEquals(user, userBasicRespone.getData());
    }

    @Test
    public void givenUserNotExistWhenFindByEmailAndPasswordThenNotFound(){
        String email = user.getEmail();
        String password = user.getPassword();

        given(userRepo.getUserByEmailAndPassword(email, password)).willReturn(null);

        BasicRespone<User>  userBasicRespone = userService.findByEmailAndPassword(email, password);
        assertEquals(-1, userBasicRespone.getCode());
        assertEquals("User not exist", userBasicRespone.getMessage());
        assertNull(userBasicRespone.getData());
    }

    @Test
    public void givenEmailExistAndSaveSuccessWhenUpdateResetPasswordThenUpdateSuccess(){
        String email = user.getEmail();
        String resetPasswordToken = "AMOIBYILauwTi2CRjTrAU0tC4aGOT21QXi6DGH7if7eikQXO6ShUSF5";

        given(userRepo.getUserByEmail(email)).willReturn(user);
        user.setResetPasswordToken(resetPasswordToken);
        given(userRepo.save(user)).willReturn(user);

        BasicRespone<User>  userBasicRespone = userService.updateResetPassword(resetPasswordToken, email);
        assertEquals(0, userBasicRespone.getCode());
        assertEquals("Update reset password success", userBasicRespone.getMessage());
        assertEquals(user, userBasicRespone.getData());
    }

    @Test
    public void givenSaveUserInDataFailWhenUpdateResetPasswordThenNotUpdate(){
        String email = user.getEmail();
        String resetPasswordToken = "AMOIBYILauwTi2CRjTrAU0tC4aGOT21QXi6DGH7if7eikQXO6ShUSF5";

        given(userRepo.getUserByEmail(email)).willReturn(user);
        user.setResetPasswordToken(resetPasswordToken);
        given(userRepo.save(user)).willReturn(null);

        BasicRespone<User>  userBasicRespone = userService.updateResetPassword(resetPasswordToken, email);
        assertEquals(-1, userBasicRespone.getCode());
        assertEquals("Update reset password not success", userBasicRespone.getMessage());
        assertNull(userBasicRespone.getData());
    }

    @Test
    public void givenEmailNotExistWhenUpdateResetPasswordThenNotUpdate(){
        String email = user.getEmail();
        String resetPasswordToken = "AMOIBYILauwTi2CRjTrAU0tC4aGOT21QXi6DGH7if7eikQXO6ShUSF5";

        given(userRepo.getUserByEmail(email)).willReturn(null);
        given(userRepo.save(user)).willReturn(user);

        BasicRespone<User>  userBasicRespone = userService.updateResetPassword(resetPasswordToken, email);
        assertEquals(-1, userBasicRespone.getCode());
        assertEquals("email not exist", userBasicRespone.getMessage());
        assertNull(userBasicRespone.getData());
    }
}
