package com.thorpora.module.user;


import com.thorpora.module.user.fixture.UserFixtures;
import com.thorpora.module.user.service.UserService;
import com.thorpora.test.env.AbstractMockEnvIT;
import com.thorpora.test.junit.TestDecorator;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class UserServiceIT extends AbstractMockEnvIT {

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