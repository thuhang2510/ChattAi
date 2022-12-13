package com.example.chattai.controllers;

import com.example.chattai.model.User;
import com.example.chattai.request.LoginRequest;
import com.example.chattai.respone.BasicRespone;
import com.example.chattai.services.UserService;
import com.example.chattai.utils.EmailUtil;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.example.chattai.utils.Utility;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private EmailUtil emailUtil;

    private User user;

    @Before
    public void init() {
        user = new User(1, "nguyen", "mi", "tunguyet2510@gmail.com", "thuhang123", "nu");
    }

    @Test
    public void givenValidInputWhenLogInThenLogInSuccess() throws Exception {
        LoginRequest loginRequest = new LoginRequest("tunguyet2510@gmail.com", "thuhang123");
        given(userService.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword()))
                .willReturn(new BasicRespone<>("User exist", 0, user));

        MvcResult mvcResult = mvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Utility.mapToJson(loginRequest)))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        assertEquals(200, status);
        assertEquals("Get token success", jsonObject.get("message"));
        assertEquals(0, jsonObject.get("code"));
        assertFalse(jsonObject.isNull("data"));
    }

    @Test
    public void givenInvalidEmailWhenLogInThenLogInFail() throws Exception {
        LoginRequest loginRequest = new LoginRequest("tunguyet2510gmail.com", "thuhang123");
        given(userService.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword()))
                .willReturn(new BasicRespone<>("User exist", 0, user));

        MvcResult mvcResult = mvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Utility.mapToJson(loginRequest)))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        assertEquals(200, status);
        assertEquals("Invalid email", jsonObject.get("message"));
        assertEquals(-1, jsonObject.get("code"));
        assertTrue(jsonObject.isNull("data"));
    }

    @Test
    public void givenBlankEmailWhenLogInThenLogInFail() throws Exception {
        LoginRequest loginRequest = new LoginRequest("", "thuhang123");
        given(userService.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword()))
                .willReturn(new BasicRespone<>("User exist", 0, user));

        MvcResult mvcResult = mvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Utility.mapToJson(loginRequest)))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        assertEquals(200, status);
        assertEquals("Email cannot blank", jsonObject.get("message"));
        assertEquals(-1, jsonObject.get("code"));
        assertTrue(jsonObject.isNull("data"));
    }

    @Test
    public void givenBlankPasswordWhenLogInThenLogInFail() throws Exception {
        LoginRequest loginRequest = new LoginRequest("tunguyet2510@gmail.com", "");
        given(userService.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword()))
                .willReturn(new BasicRespone<>("User exist", 0, user));

        MvcResult mvcResult = mvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Utility.mapToJson(loginRequest)))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        assertEquals(200, status);
        assertEquals("Password cannot blank", jsonObject.get("message"));
        assertEquals(-1, jsonObject.get("code"));
        assertTrue(jsonObject.isNull("data"));
    }

    @Test
    public void givenUserNotExistWhenLogInThenLogInFail() throws Exception {
        LoginRequest loginRequest = new LoginRequest("tunguyet2510@gmail.com", "thuhang123");
        given(userService.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword()))
                .willReturn(new BasicRespone<>("User not exist", -1, null));

        MvcResult mvcResult = mvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Utility.mapToJson(loginRequest)))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        assertEquals(200, status);
        assertEquals("User not exist", jsonObject.get("message"));
        assertEquals(-1, jsonObject.get("code"));
        assertTrue(jsonObject.isNull("data"));
    }

    @Test
    public void givenFindByEmailAndPasswordExceptionWhenLogInThenLogInFail() throws Exception {
        LoginRequest loginRequest = new LoginRequest("tunguyet2510@gmail.com", "thuhang123");
        given(userService.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword()))
                .willReturn(new BasicRespone<>("Exception Occured", -1, null));

        MvcResult mvcResult = mvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Utility.mapToJson(loginRequest)))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        assertEquals(200, status);
        assertEquals("Exception Occured", jsonObject.get("message"));
        assertEquals(-1, jsonObject.get("code"));
        assertTrue(jsonObject.isNull("data"));
    }

    @Test
    public void givenValidInputWhenSignUpThenSignUpSuccess() throws Exception {
        given(userService.addUser(user))
                .willReturn(new BasicRespone<>("Add user success", 0, user));

        MvcResult mvcResult = mvc.perform(post("/api/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Utility.mapToJson(user)))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);
        JSONObject data = jsonObject.getJSONObject("data");

        assertEquals(200, status);
        assertEquals("Add user success", jsonObject.get("message"));
        assertEquals(0, jsonObject.get("code"));
        assertEquals(user.getId(), data.getInt("id"));
        assertEquals(user.getEmail(), data.get("email"));
        assertEquals(user.getFirstname(), data.get("firstname"));
        assertEquals(user.getLastname(), data.get("lastname"));
    }

    @Test
    public void givenInvalidEmailWhenSignUpThenSignUpFail() throws Exception {
        given(userService.addUser(user))
                .willReturn(new BasicRespone<>("Add user success", 0, user));

        user.setEmail("tunguyet2510gmail.com");

        MvcResult mvcResult = mvc.perform(post("/api/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Utility.mapToJson(user)))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        assertEquals(200, status);
        assertEquals("Invalid email", jsonObject.get("message"));
        assertEquals(-1, jsonObject.get("code"));
        assertTrue(jsonObject.isNull("data"));
    }

    @Test
    public void givenBlankEmailWhenSignUpThenSignUpFail() throws Exception {
        given(userService.addUser(user))
                .willReturn(new BasicRespone<>("Add user success", 0, user));

        user.setEmail("");

        MvcResult mvcResult = mvc.perform(post("/api/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Utility.mapToJson(user)))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        assertEquals(200, status);
        assertEquals("Email cannot blank", jsonObject.get("message"));
        assertEquals(-1, jsonObject.get("code"));
        assertTrue(jsonObject.isNull("data"));
    }

    @Test
    public void givenBlankPasswordWhenSignUpThenSignUpFail() throws Exception {
        given(userService.addUser(user))
                .willReturn(new BasicRespone<>("Add user success", 0, user));

        user.setPassword("");

        MvcResult mvcResult = mvc.perform(post("/api/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Utility.mapToJson(user)))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        assertEquals(200, status);
        assertEquals("Password cannot blank", jsonObject.get("message"));
        assertEquals(-1, jsonObject.get("code"));
        assertTrue(jsonObject.isNull("data"));
    }

    @Test
    public void givenEmailExistWhenSignUpThenSignUpFail() throws Exception {
        given(userService.addUser(user))
                .willReturn(new BasicRespone<>("Email exist", -1, null));

        MvcResult mvcResult = mvc.perform(post("/api/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Utility.mapToJson(user)))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        assertEquals(200, status);
        assertEquals("Email exist", jsonObject.get("message"));
        assertEquals(-1, jsonObject.get("code"));
        assertTrue(jsonObject.isNull("data"));
    }

    @Test
    public void givenAddUserFailWhenSignUpThenSignUpFail() throws Exception {
        given(userService.addUser(user))
                .willReturn(new BasicRespone<>("Add user failed", -1, null));

        MvcResult mvcResult = mvc.perform(post("/api/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Utility.mapToJson(user)))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        assertEquals(200, status);
        assertEquals("Add user failed", jsonObject.get("message"));
        assertEquals(-1, jsonObject.get("code"));
        assertTrue(jsonObject.isNull("data"));
    }

    @Test
    public void givenAddUserExceptionWhenSignUpThenSignUpFail() throws Exception {
        given(userService.addUser(user))
                .willReturn(new BasicRespone<>("Exception", -1, null));

        MvcResult mvcResult = mvc.perform(post("/api/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Utility.mapToJson(user)))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        assertEquals(200, status);
        assertEquals("Exception", jsonObject.get("message"));
        assertEquals(-1, jsonObject.get("code"));
        assertTrue(jsonObject.isNull("data"));
    }
}
