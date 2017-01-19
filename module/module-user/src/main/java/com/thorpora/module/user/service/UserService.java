/**
 * Created by Yannick Lacaute on 22/12/16.
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
package com.thorpora.module.user.service;

import com.thorpora.module.core.service.AbstractRestService;
import com.thorpora.module.user.web.UserResource;
import com.thorpora.module.user.domain.User;
import com.thorpora.module.user.exception.UserConflictException;
import com.thorpora.module.user.exception.UserNotFoundException;
import com.thorpora.module.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService extends AbstractRestService<User> {

    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;
    private UserMailService userMailService;

    public UserService(UserRepository userRepository, UserMailService userMailService) {
        this.userRepository = userRepository;
        this.userMailService = userMailService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User createUser(User user) {
        userRepository.save(user);
        userMailService.sendRegistrationMail(user);
        log.info("User created (login: {}), an activation email have been sent", user);
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public User getOne(UUID userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        return user;
    }

    @Transactional
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public Iterable<User> save(Collection<User> users) {
        Iterable<User> savedUsers = userRepository.save(users);
        log.debug("{} user(s) have been saved", users.size());
        return savedUsers;
    }

    @Transactional
    @Override
    public User updateOne(UUID pathId, User entity) {
        entity.setId(pathId);
        return userRepository.save(entity);
    }

    @Transactional
    @Override
    public Optional<User> deleteOne(UUID id) {
        userRepository.delete(id);
        return Optional.ofNullable(null);
    }

    public void throwConflictIfExists(UserResource user) {
        Optional<User> existingUser = userRepository.findOneByLogin(user.getLogin());
        if (existingUser.isPresent()) {
            throw new UserConflictException(existingUser.get(), "Login already exists");
        }
        existingUser = userRepository.findOneByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new UserConflictException(existingUser.get(), "Email already exists.");
        }
    }

}
