package com.hao.demo.api;

import com.hao.demo.App;
import com.hao.demo.bean.User;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserResourceV2IntergrationTest {

    private int port = 8080;

    @Autowired
    TestRestTemplate restTemplate;

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void getUserOne() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.withBasicAuth("user1","pass1")
                .exchange(createURLWithPort("/api/v2/user/1"),
                        HttpMethod.GET,
                        entity,
                        String.class);

        String expected = "{\"id\":1,\"firstName\":\"Hao\",\"lastName\":\"Deng\",\"_links\":{\"all-users\":{\"href\":\"http://localhost:8080/api/v2/users\"},\"self\":{\"href\":\"http://localhost:8080/api/v2/user/1\"}}}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void createUser() {
        User newUser = new User();
        newUser.setFirstName("Mikky");
        newUser.setLastName("Mouse");

        HttpEntity<User> entity = new HttpEntity<User>(newUser, headers);

        ResponseEntity<String> response = restTemplate.withBasicAuth("user1","pass1")
                .exchange(createURLWithPort("/api/v2/user"),
                        HttpMethod.POST,
                        entity,
                        String.class);

        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

        Assert.assertTrue(actual.contains("/api/v2/user/5"));

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}