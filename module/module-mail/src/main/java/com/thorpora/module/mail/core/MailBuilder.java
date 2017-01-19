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
package com.thorpora.module.mail.core;

import com.thorpora.module.mail.domain.Mail;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MailBuilder {

    private String recipient;
    private String subject;
    private String templateName;
    private String langageTag;
    private boolean isMultipart = false;
    private boolean isHtml = true;
    private Map<String, Serializable> parameters;

    private MailBuilder() {
        parameters = new HashMap<>();
    }

    public static MailBuilder builder() {
        return new MailBuilder();
    }

    public MailBuilder langageTag(String langageTag) {
        this.langageTag = langageTag;
        return this;
    }

    public MailBuilder to(String recipient) {
        this.recipient = recipient;
        return this;
    }

    public MailBuilder subject(String subject) {
        this.subject = subject;
        return this;
    }

    public MailBuilder isMultipart(boolean isMultipart) {
        this.isMultipart = isMultipart;
        return this;
    }

    public MailBuilder isHtml(boolean isHtml) {
        this.isHtml = isHtml;
        return this;
    }

    public MailBuilder template(String templateName) {
        this.templateName = templateName;
        return this;
    }

    public MailBuilder setParameter(String name, Serializable value) {
        parameters.put(name, value);
        return this;
    }

    public Mail build() {
        return new Mail(recipient, subject, templateName, langageTag, parameters, isMultipart, isHtml);
    }


}
