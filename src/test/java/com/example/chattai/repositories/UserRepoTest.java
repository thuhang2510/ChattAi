package com.example.chattai.repositories;

import com.example.chattai.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepoTest {
    @Autowired
    private UserRepo userRepo;

    @Test
    @Sql("/createUser.sql")
    public void givenValidInfoWhenGetUserByEmailAndPasswordThenGetUserSuccess(){
        String email = "hangdamthu4@gmail.com";
        String password = "nguyenmi1131";

        User user = userRepo.getUserByEmailAndPassword(email, password);

        Assert.assertEquals("mi", user.getLastname());
        Assert.assertEquals(email, user.getEmail());
        Assert.assertEquals(password, user.getPassword());
        Assert.assertEquals("nu", user.getGender());
        Assert.assertEquals("nguyen", user.getFirstname());
    }

    @Test
    @Sql("/createUser.sql")
    public void givenInvalidInfoWhenGetUserByEmailAndPasswordThenNotFindUser(){
        String email = "hangdamthu4gmail.com";
        String password = "nguyenmi1131";

        User user = userRepo.getUserByEmailAndPassword(email, password);
        Assert.assertNull(user);
    }

    @Test
    @Sql("/createUser.sql")
    public void givenUserInfoNotExistWhenGetUserByEmailAndPasswordThenNotFindUser(){
        String email = "hangdamthu4@gmail.com";
        String password = "damhang";

        User user = userRepo.getUserByEmailAndPassword(email, password);
        Assert.assertNull(user);
    }

    @Test
    @Sql("/createUser.sql")
    public void givenValidEmailWhenGetUserByEmailThenGetUserSuccess(){
        String email = "hangdamthu4@gmail.com";

        User user = userRepo.getUserByEmail(email);

        Assert.assertEquals("mi", user.getLastname());
        Assert.assertEquals(email, user.getEmail());
        Assert.assertEquals("nguyenmi1131", user.getPassword());
        Assert.assertEquals("nu", user.getGender());
        Assert.assertEquals("nguyen", user.getFirstname());
    }

    @Test
    @Sql("/createUser.sql")
    public void givenInvalidEmailWhenGetUserByEmailThenNotFindUser(){
        User user = userRepo.getUserByEmail("hangdamthu4gmail.com");
        Assert.assertNull(user);
    }

    @Test
    @Sql("/createUser.sql")
    public void givenEmailNotExistWhenGetUserByEmailThenNotFindUser(){
        User user = userRepo.getUserByEmail("hangdamthu5@gmail.com");
        Assert.assertNull(user);
    }
}
