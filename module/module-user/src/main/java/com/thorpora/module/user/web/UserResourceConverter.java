/**
 * Created by Yannick Lacaute on 12/01/17.
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
package com.thorpora.module.user.web;

import com.thorpora.module.core.web.AbstractRestResourceConverter;
import com.thorpora.module.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserResourceConverter extends AbstractRestResourceConverter<UserResource, User> {

    @Override
    public UserResource toResource(User entity) {
        UserResource user = new UserResource();
        user.setId(entity.getId());
        user.setHref(entity.getHref());
        user.setLogin(entity.getLogin());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setEmail(entity.getEmail());
        user.setActivated(entity.isActivated());
        user.setLangKey(entity.getLangKey());
        user.setPassword(entity.getPassword());
        return user;
    }

    @Override
    public List<UserResource> toResource(List<User> users) {
        return users.stream()
                .map(this::toResource)
                .collect(Collectors.toList());
    }

    @Override
    public User toEntity(UserResource resource) {
        User user = new User();
        user.setHref(resource.getHref());
        user.setLogin(resource.getLogin());
        user.setFirstName(resource.getFirstName());
        user.setLastName(resource.getLastName());
        user.setEmail(resource.getEmail());
        user.setActivated(resource.isActivated());
        user.setLangKey(resource.getLangKey());
        user.setPassword(resource.getPassword());
        return user;
    }

    @Override
    public List<User> toEntity(List<UserResource> entities) {
        return entities.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

}
