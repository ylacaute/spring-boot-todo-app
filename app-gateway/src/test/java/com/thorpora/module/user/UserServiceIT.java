package com.thorpora.module.user;


import com.thorpora.module.user.fixture.UserFixtures;
import com.thorpora.module.user.service.UserService;
import com.thorpora.test.ITContext;
import com.thorpora.test.TestDecorator;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@SpringBootTest(
        classes = ITContext.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Transactional
public class UserServiceIT {

    private final static Logger log = LoggerFactory.getLogger(UserServiceIT.class);

    @Inject
    private UserService userService;

    @Rule
    public TestDecorator testDecorator = new TestDecorator();

    @Test
    public void user_findAllAndSaveOne_validGet() {
        final int initialUserCount = 0;
        Assertions.assertThat(userService.findAll()).hasSize(initialUserCount);
        userService.createUser(UserFixtures.create());
        Assertions.assertThat(userService.findAll()).hasSize(initialUserCount + 1);
    }

    @Test
    public void user_saveAndGetById_validGet() {
        final int initialUserCount = 0;
        Assertions.assertThat(userService.findAll()).hasSize(initialUserCount);
        userService.createUser(UserFixtures.create());
        Assertions.assertThat(userService.findAll()).hasSize(initialUserCount + 1);
    }
}