package com.hao.demo.api;

import com.hao.demo.bean.User;
import com.hao.demo.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Base64Utils;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserResourceV1.class)
public class UserResourceV1Test {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void getUserOne() throws Exception {
        User user = new User(1L, "Deng", "Hao");
        Mockito.when(
                userService.getById(Mockito.eq(1L)))
                .thenReturn(user);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/user/1")
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString("user1:pass1".getBytes()))
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());
        String expected = "{\"id\":1,\"firstName\":\"Deng\",\"lastName\":\"Hao\",\"_links\":{\"all-users\":{\"href\":\"http://localhost/api/v1/users\"},\"self\":{\"href\":\"http://localhost/api/v1/user/1\"}}}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void createUser() throws Exception {
        String createUserJson = "{\"id\":4,\"firstName\": \"Alex\",\"lastName\": \"Pato\"}";
        User newUser = new User(4L, "Alex", "Pato");

        Mockito.when(
                userService.createUser(Mockito.any(User.class))).thenReturn(newUser);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/user")
                .accept(MediaType.APPLICATION_JSON).content(createUserJson)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString("user1:pass1".getBytes()))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        Assert.assertEquals("http://localhost/api/v1/user/4",
                response.getHeader(HttpHeaders.LOCATION));
    }
}