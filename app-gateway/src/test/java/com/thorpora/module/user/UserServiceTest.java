/**
 * Created by Yannick Lacaute on 21/01/17.
 * Copyright 2015-2016 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thorpora.module.user;

import com.thorpora.module.mail.core.MailService;
import com.thorpora.module.user.fixture.UserFixtures;
import com.thorpora.module.user.repository.UserRepository;
import com.thorpora.module.user.service.UserService;
import com.thorpora.test.junit.TestDecorator;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.MockitoAnnotations.initMocks;


public class UserServiceTest {

    private final static Logger log = LoggerFactory.getLogger(UserServiceTest.class);

    @Rule
    public TestDecorator testDecorator = new TestDecorator();

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MailService mailService;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void user_findAll_empty() {
        Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<>());
        Assertions.assertThat(userService.findAll()).hasSize(0);
        System.out.println("");
    }

    @Test
    public void user_findAll_onResult() {
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(UserFixtures.create()));
        Assertions.assertThat(userService.findAll()).hasSize(1);
        System.out.println("");
    }

    @Test
    public void user_deleteNotExist_optionalNotPresent() {
        Assertions.assertThat(userService.deleteOne(UUID.randomUUID()).isPresent()).isFalse();
    }

}
