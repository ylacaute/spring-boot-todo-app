package com.thorpora.gateway.user;

import com.thorpora.gateway.AbstractIT;
import com.thorpora.module.core.error.ErrorDTO;
import com.thorpora.module.todo.TodoResource;
import com.thorpora.module.todo.web.TodoController;
import com.thorpora.module.user.fixture.UserFixtures;
import com.thorpora.module.user.repository.UserRepository;
import com.thorpora.module.user.web.UserController;
import com.thorpora.module.user.web.UserResource;
import com.thorpora.module.user.web.UserResourceConverter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration Tests are NOT transactional, we need to clear DB before each test.
 */
public class UserControllerIT extends AbstractIT {

    @Inject
    private TestRestTemplate restTemplate;

    @Inject
    private UserRepository userRepository;

    private static UserResourceConverter converter = new UserResourceConverter();


    @Before
    public void beforeTest() {
        userRepository.deleteAll();
    }

    /**
     * Testing:
     *   POST ONE
     *   GET ONE
     */
    @Test
    public void getManyEmpty() {
        ResponseEntity<List<UserResource>> getResponse =
                restTemplate.exchange(TodoController.TODOS_MAPPING, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<UserResource>>() {});

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<UserResource> resources = getResponse.getBody();
        assertThat(resources).hasSize(0);
    }

    /**
     * Testing:
     *   POST ONE
     *   GET ONE
     */
    @Test
    public void postOneAndGetIt() {
        UserResource user = converter.toResource(UserFixtures.create());

        ResponseEntity<UserResource> postResponse = restTemplate
                .postForEntity(UserController.USER_MAPPING, user, UserResource.class);

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getBody()).isNotNull();
        String path = postResponse.getHeaders().getLocation().getPath();
        assertThat(path).matches(buildGetUserPath(UUID_PATTERN));

        ResponseEntity<UserResource> getResponse = restTemplate
                .getForEntity(path, UserResource.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        UserResource resource = getResponse.getBody();
        assertThat(resource.getId()).isNotNull();
        assertThat(resource.getLastName()).isEqualTo(user.getLastName());
        assertThat(resource.getEmail()).isEqualTo(user.getEmail());
    }

    /**
     * Testing:
     *   POST ONE
     *   GET MANY
     */
    @Test
    public void postManyAndGetThem() {
        UserResource user1 = converter.toResource(UserFixtures.create());
        UserResource user2 = converter.toResource(UserFixtures.create());

        restTemplate.postForEntity(UserController.USER_MAPPING, user1, Object.class);
        restTemplate.postForEntity(UserController.USER_MAPPING, user2, Object.class);

        ResponseEntity<List<UserResource>> getResponse =
                restTemplate.exchange(UserController.USERS_MAPPING, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<UserResource>>() {});

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        List<UserResource> resources = getResponse.getBody();
        assertThat(resources).hasSize(2);
    }

    /**
     * Testing:
     *   POST ONE
     *   PUT ONE
     *   GET ONE
     */
    @Test
    public void postOnePutItAndGetIt() {
        String newEmail = "sample@free.fr";
        UserResource user = converter.toResource(UserFixtures.create());
        assertThat(user.getEmail()).isNotEqualTo(newEmail);

        // POST
        UserResource savedUser = restTemplate
                .postForEntity(UserController.USER_MAPPING, user, UserResource.class).getBody();

        // PUT
        user.setEmail(newEmail);
        restTemplate.put(UserController.USER_ID_MAPPING, user, savedUser.getId());

        // GET
        ResponseEntity<UserResource> getResponse = restTemplate
                .getForEntity(UserController.USER_ID_MAPPING, UserResource.class, savedUser.getId());

        UserResource resource = getResponse.getBody();
        assertThat(resource.getEmail()).isEqualTo(newEmail);
    }

    /**
     * Testing:
     *   POST ONE
     *   DELETE ONE
     *   GET ONE NOT FOUND
     */
    @Test
    public void postOneDeleteItAndGetItNotFound() {
        UserResource user = converter.toResource(UserFixtures.create());

        // POST
        UserResource savedUser = restTemplate
                .postForEntity(UserController.USER_MAPPING, user, UserResource.class).getBody();

        // DELETE
        restTemplate.delete(UserController.USER_ID_MAPPING, savedUser.getId());

        // GET
        ResponseEntity<ErrorDTO> getResponse = restTemplate
                .getForEntity(UserController.USER_ID_MAPPING, ErrorDTO.class, savedUser.getId());

        ErrorDTO errorDTO = getResponse.getBody();
        assertThat(errorDTO.getTimeStamp()).isNotNull();
        assertThat(errorDTO.getPath()).isEqualTo(buildGetUserPath(savedUser.getId()));
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    private String buildGetUserPath(UUID userId) {
        return UserController.USER_ID_MAPPING.replace("{id}", userId.toString());
    }

    private String buildGetUserPath(String userId) {
        return UserController.USER_ID_MAPPING.replace("{id}", userId);
    }

}