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

import com.thorpora.module.user.web.UserResource;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class UserResourceFixtures {

    private static AtomicLong idCounter = new AtomicLong(0L);
    private UserResource user;

    private UserResourceFixtures() {
        user = createDefaultUser();
    }

    public static UserResource create() {
        return createDefaultUser();
    }

    public static List<UserResource> create(int resourceCount) {
        return IntStream
                .range(0, resourceCount)
                .mapToObj(i -> createDefaultUser())
                .collect(Collectors.toList());
    }

    public static UserResourceFixtures builder() {
        return new UserResourceFixtures();
    }

    public UserResourceFixtures login(String login) {
        user.setLogin(login);
        return this;
    }

    public UserResourceFixtures id(UUID id) {
        user.setId(id);
        return this;
    }

    public UserResource build() {
        return user;
    }

    public static List<UserResource> generate(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> UserResourceFixtures.createDefaultUser())
                .collect(Collectors.toList());
    }

    public static UserResource createDefaultUser() {
        Long id = idCounter.getAndIncrement();
        UserResource user = new UserResource();
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
