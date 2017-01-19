package com.thorpora.gateway.user;

import com.thorpora.gateway.AbstractIT;
import com.thorpora.module.user.fixture.UserFixtures;
import com.thorpora.module.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import javax.inject.Inject;

public class UserServiceIAT extends AbstractIT {

    @Inject
    private UserService userService;

    @Test
    public void findAllAndSaveOne() {
        Assertions.assertThat(userService.findAll()).hasSize(2);
        userService.createUser(UserFixtures.create());
        Assertions.assertThat(userService.findAll()).hasSize(3);
    }

    @Test
    public void saveAndGetById() {
        Assertions.assertThat(userService.findAll()).hasSize(2);
        userService.createUser(UserFixtures.create());
        Assertions.assertThat(userService.findAll()).hasSize(3);
    }
}