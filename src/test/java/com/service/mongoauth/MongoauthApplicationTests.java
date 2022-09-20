package com.service.mongoauth;

import com.service.mongoauth.config.jwt.JwtResponse;
import com.service.mongoauth.dto.UserDto;
import com.service.mongoauth.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MongoauthApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String root_url = "http://localhost:%d";

    @Test
    void testUserCreation() {
        User user = new User();
        user.setEmail("test12345@example.com");
        user.setPassword("test12345");
        user.setName("John Doe");

        assertEquals(testRestTemplate.postForEntity(getUrl("user/createUser"), user, UserDto.class)
                        .getStatusCode(),
                HttpStatus.OK);
    }

    @Test
    void testUserAuthentication() {
        User user = new User();
        user.setEmail("test12345@example.com");
        user.setPassword("test12345");

        assertEquals(testRestTemplate.postForEntity(getUrl("authenticate"), user, JwtResponse.class)
                        .getStatusCode(),
                HttpStatus.OK);
    }

    private final String getUrl(String path) {
        return !path.isBlank() || path != null ? String.join("/", String.format(root_url, port), path)
                : String.format(root_url, port);
    }

}
