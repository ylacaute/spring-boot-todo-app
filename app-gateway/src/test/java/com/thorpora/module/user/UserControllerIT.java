package com.thorpora.module.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thorpora.gateway.core.db.DBCleaner;
import com.thorpora.module.user.domain.User;
import com.thorpora.module.user.fixture.UserResourceFixtures;
import com.thorpora.module.user.repository.UserRepository;
import com.thorpora.module.user.web.UserController;
import com.thorpora.module.user.web.UserResource;
import com.thorpora.test.context.ITContext;
import com.thorpora.test.env.AbstractServletEnvIT;
import com.thorpora.test.junit.TestDecorator;
import com.thorpora.test.rest.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Convention name test: resource_action_expectedBehavior
 */
public class UserControllerIT extends AbstractServletEnvIT {

    @Inject
    private ObjectMapper jMapper;

    @Inject
    private DBCleaner dbCleaner;

    @Inject
    private TestRestTemplate restTemplate;

    @Rule
    public TestDecorator testDecorator = new TestDecorator();

    @Inject
    private UserRepository userRepository;

    private TestRestHelper<UserResource, User> restTest;

    private final UUID randomUUUID = UUID.randomUUID();

    @PostConstruct
    public void beforeClass() {
        restTest = new TestRestHelper<UserResource, User>(
                restTemplate,
                UserController.USER_MAPPING,
                UserController.USER_ID_MAPPING,
                UserController.USERS_MAPPING) {
            @Override
            protected ParameterizedTypeReference<List<UserResource>> getResourceListType() {
                return new ParameterizedTypeReference<List<UserResource>>() {};
            }
        };
    }

    @Before
    public void beforeTest() {
        dbCleaner.clear("users");
    }

    @Test
    public void user_getMany_validNoResult() {
        restTest.getManyWithoutResult();
    }

    @Test
    public void user_postOne_validPost() {
        ResourceCreator<UserResource> creator = UserResourceFixtures::create;
        ResourceVerifier<UserResource> verifier = this::genericVerifier;
        restTest.postOne(creator, verifier);
    }

    @Test
    public void user_postOneAndGetIt_validGet() {
        ResourceCreator<UserResource> creator = UserResourceFixtures::create;
        ResourceVerifier<UserResource> verifier = this::genericVerifier;
        restTest.postOneAndGetIt(creator, verifier);
    }

    @Test
    public void task_postManyAndGetThem_validGet() {
        final int userCount = 5;
        final int specificTaskTaskCount = 3;

        ResourceListCreator<UserResource> creator = () -> {
            Collection<UserResource> resources = UserResourceFixtures
                    .create(userCount - 1);
            resources.add(UserResourceFixtures.builder()
                    .login(randomUUUID.toString())
                    .build());
            return resources;
        };

        ResourceListVerifier<UserResource> verifier = (resourcesGot) -> {

            // Verify we got all elements
            assertThat(resourcesGot).hasSize(userCount);

            // Verify a particular element
            Optional<UserResource> task = resourcesGot.stream()
                    .filter(r -> r.getLogin().equals(randomUUUID.toString()))
                    .findFirst();
            assertThat(task.isPresent()).isTrue();
        };

        restTest.postManyAndGetThem(creator, verifier);
    }

    @Test
    public void user_postOnePutItWithoutModificationAndGetIt_validGet() {
        ResourceCreator<UserResource> creator = UserResourceFixtures::create;
        ResourceModifier<UserResource> modifier = (r) -> r;
        ResourceVerifier<UserResource> verifier = this::genericVerifier;
        restTest.postOnePutItAndGetIt(creator, modifier, verifier);
    }

    @Test
    public void user_postOnePutItAndGetIt_validGet() {
        final String initalLogin = "login";

        ResourceCreator<UserResource> creator = () ->
                UserResourceFixtures.builder().login(initalLogin).build();
        ResourceModifier<UserResource> modifier = (r) -> {
            r.setLogin(randomUUUID.toString());
            return r;
        };
        ResourceVerifier<UserResource> verifier = (postedRrc, rscGot) -> {
            assertThat(rscGot.getLogin()).isEqualTo(randomUUUID.toString());
        };

        restTest.postOnePutItAndGetIt(creator, modifier, verifier);
    }

    @Test
    public void user_postOneDeleteItAndGetItNotFound_validGet() {
        ResourceCreator<UserResource> creator = UserResourceFixtures::create;
        restTest.postOneDeleteItAndGetItNotFound(creator);
    }

    @Test
    public void user_deleteNotExist_validDelete() {
        final UUID unexistingUserId = UUID.randomUUID();
        restTest.deleteNotExist(unexistingUserId);
    }

    private void genericVerifier(UserResource postedResource, UserResource resourceGot) {
        assertThat(postedResource.getEmail()).isEqualTo(resourceGot.getEmail());
    }

}