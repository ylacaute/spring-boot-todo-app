/**
 * Created by Yannick Lacaute on 31/12/16.
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
package com.thorpora.module.user.fixture;

import com.thorpora.module.user.domain.User;
import com.thorpora.module.user.web.UserController;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class UserFixtures {

    private static AtomicLong idCounter = new AtomicLong(0L);
    private User user;

    private UserFixtures() {
        user = createDefaultUser();
    }

    public static User create() {
        return createDefaultUser();
    }

    public static List<User> create(int resourceCount) {
        return IntStream
                .range(0, resourceCount)
                .mapToObj(i -> createDefaultUser())
                .collect(Collectors.toList());
    }

    public static UserFixtures builder() {
        return new UserFixtures();
    }

    public UserFixtures login(String login) {
        user.setLogin(login);
        return this;
    }

    public UserFixtures id(UUID id) {
        user.setId(id);
        return this;
    }

    public User build() {
        return user;
    }

    public static List<User> generate(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> UserFixtures.createDefaultUser())
                .collect(Collectors.toList());
    }

    public static User createDefaultUser() {
        Long id = idCounter.getAndIncrement();
        User user = new User();
        user.setHref(UserController.USER_ID_MAPPING
                .replace("{id}", user.getId().toString()));
        user.setFirstName("FirstName_" + id);
        user.setActivated(true);
        user.setEmail("yannick.lacaute+" + id + "@gmail.com");
        user.setLangKey("FR");
        user.setLastName("LastName_" + id);
        user.setLogin("login_" + id);
        user.setPassword("password");
        user.setResetDate(ZonedDateTime.now());
        user.setResetKey("resetKey");
        return user;
    }


}
