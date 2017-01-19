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
package com.thorpora.module.mail.domain;

import com.thorpora.module.core.jpa.converter.StringMapConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

@Entity
public class Mail {

    @Id
    private UUID id;

    @Column
    private String recipient;

    @Column
    private String subject;

    @Column
    private String templateName;

    @Column
    private String langageTag;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = StringMapConverter.class)
    private Map<String, Serializable> parameters;

    @Column
    private boolean isMultipart ;

    @Column
    private boolean isHtml;


    public Mail() {
        id = UUID.randomUUID();
    }

    public Mail(String recipient, String subject, String templateName, String langageTag, Map<String, Serializable> parameters,
                boolean isMultipart, boolean isHtml) {
        this();
        this.recipient = recipient;
        this.subject = subject;
        this.templateName = templateName;
        this.isMultipart = isMultipart;
        this.isHtml = isHtml;
        this.langageTag = langageTag;
        this.parameters = parameters;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Map<String, Serializable> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Serializable> parameters) {
        this.parameters = parameters;
    }

    public String getLangageTag() {
        return langageTag;
    }

    public void setLangageTag(String langageTag) {
        this.langageTag = langageTag;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public boolean isMultipart() {
        return isMultipart;
    }

    public void setMultipart(boolean multipart) {
        isMultipart = multipart;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public void setHtml(boolean html) {
        isHtml = html;
    }


}
