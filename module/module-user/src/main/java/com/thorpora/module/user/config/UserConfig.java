/**
 * Created by Yannick Lacaute on 30/12/16.
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
package com.thorpora.module.user.config;

import com.thorpora.module.mail.core.MailService;
import com.thorpora.module.user.web.UserController;
import com.thorpora.module.user.repository.UserRepository;
import com.thorpora.module.user.service.UserMailService;
import com.thorpora.module.user.service.UserService;
import org.springframework.context.annotation.Bean;

public class UserConfig {

    @Bean
    public UserController userController(UserService userService) {
        return new UserController(userService);
    }

    @Bean
    public UserMailService userMailService(MailService mailService) {
        return new UserMailService(mailService);
    }

    @Bean
    public UserService userService(UserRepository userRepository, UserMailService userMailService) {
        return new UserService(userRepository, userMailService);
    }

}
