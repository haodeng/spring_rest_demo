package com.hao.demo.api;

import com.hao.demo.bean.User;
import com.hao.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserResourceV1.class, secure = false)
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
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());
        String expected = "{\"id\":1,\"firstName\":\"Deng\",\"lastName\":\"Hao\",\"_links\":{\"all-users\":{\"href\":\"http://localhost/api/v1/users\"},\"self\":{\"href\":\"http://localhost/api/v1/user/1\"}}}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }
}