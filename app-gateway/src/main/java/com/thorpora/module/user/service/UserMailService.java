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
package com.thorpora.module.user.service;

import com.thorpora.module.mail.core.MailBuilder;
import com.thorpora.module.mail.core.MailService;
import com.thorpora.module.mail.domain.Mail;
import com.thorpora.module.user.domain.User;

public class UserMailService {

    private final static String ACTIVATION_TITLE_KEY = "email.activation.title";
    private final static String ACTIVATION_TEMPLATE = "sample";

    private MailService mailService;

    public UserMailService(MailService mailService) {
        this.mailService = mailService;
    }

    public void sendRegistrationMail(User user) {
        Mail registrationEmail = MailBuilder.builder()
                .to(user.getEmail())
                .subject(ACTIVATION_TITLE_KEY)
                .langageTag(user.getLangKey())
                .template(ACTIVATION_TEMPLATE)
                .setParameter("user", user)
                .build();
        mailService.sendEmail(registrationEmail);
    }

}
