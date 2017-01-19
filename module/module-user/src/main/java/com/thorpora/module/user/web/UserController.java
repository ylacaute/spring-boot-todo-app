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
package com.thorpora.module.user.web;

import com.thorpora.module.core.web.AbstractRestController;
import com.thorpora.module.user.domain.User;
import com.thorpora.module.user.fixture.UserFixtures;
import com.thorpora.module.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class UserController extends AbstractRestController<UserResource, User> {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    public static final String USER_MAPPING = "/api/user";
    public static final String USER_ID_MAPPING = "/api/user/{id}";
    public static final String USERS_MAPPING = "/api/users";

    private UserService userService;

    public UserController(UserService userService) {
        super(USER_MAPPING, userService, new UserResourceConverter());
        this.userService = userService;
    }

    /**
     * POST
     */
    @PostMapping(USER_MAPPING)
    @Override
    public ResponseEntity<UserResource> postRequest(@RequestBody UserResource resource) {
        return super.postRequest(resource);
    }

    /**
     * GET ONE
     */
    @GetMapping(USER_ID_MAPPING)
    @Override
    public ResponseEntity<UserResource> getRequest(@PathVariable UUID id) {
        return super.getRequest(id);
    }

    /**
     * GET MANY
     */
    @GetMapping(USERS_MAPPING)
    @Override
    public ResponseEntity<List<UserResource>> getListRequest() {
        return super.getListRequest();
    }

    /**
     * PUT ONE
     */
    @PutMapping(USER_ID_MAPPING)
    @Override
    public ResponseEntity<UserResource> putRequest(@PathVariable UUID id,
                                                   @RequestBody UserResource resource) {
        return super.putRequest(id, resource);
    }

    /**
     * DELETE ONE
     */
    @DeleteMapping(USER_ID_MAPPING)
    @Override
    public ResponseEntity<Void> deleteRequest(@PathVariable UUID id) {
        return super.deleteRequest(id);
    }




    @GetMapping("test")
    public ResponseEntity<String> test() throws URISyntaxException {
        log.debug("REST request to list users");
        Random rn = new Random();
        int i = rn.nextInt();
        User user = UserFixtures.create();
        user.setEmail("yannick.lacaute+" + i + "@gmail.com");
        userService.createUser(user);

        return ResponseEntity
                .ok()
                .body("ok boy");
    }



// TEMP
    @RequestMapping(value = "illegal", method = RequestMethod.GET)
    public void throwIllegalArgumentException() {
        throw new IllegalArgumentException("Illegal argument !");
    }

    //@RequestMapping(value = "config", method = RequestMethod.GET)
    //public void throwConfigException() {
    //    throw new ServerConfigurationException("Bad properties file path");
    //}


}
