package br.edu.utfpr.pb.pw26s.server;

import br.edu.utfpr.pb.pw26s.server.model.User;
import br.edu.utfpr.pb.pw26s.server.repository.UserRepository;
import br.edu.utfpr.pb.pw26s.server.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LoginControllerTest {

    private static final String URL_LOGIN = "/login";
    private static final String URL_USER_INFO = "/login/user-info";

    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @BeforeEach
    public void cleanup() {
        userRepository.deleteAll();
        testRestTemplate.getRestTemplate().getInterceptors().clear();
    }

    @Test
    public void postLogin_withoutUserCredentials_receiveUnauthorized() {
        ResponseEntity<Object> response = login(Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postLogin_withInvalidUserCredentials_receiveUnauthorized() {
        ResponseEntity<Object> response = login(getValidUser(), Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postLogin_withValidUserCredentials_receiveOk() {
        userService.save(getValidUser());
        ResponseEntity<Object> response = login(getValidUser(), Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private User getValidUser() {
        User user = new User();
        user.setUsername("test-user");
        user.setDisplayName("test-display");
        user.setPassword("P4ssword");
        return user;
    }

    public <T> ResponseEntity<T> login(Class<T> responseType) {
        return testRestTemplate.postForEntity(URL_LOGIN, null, responseType);
    }

    public <T> ResponseEntity<T> login(Object request, Class<T> responseType) {
        return testRestTemplate.postForEntity(URL_LOGIN, request, responseType);
    }

}
