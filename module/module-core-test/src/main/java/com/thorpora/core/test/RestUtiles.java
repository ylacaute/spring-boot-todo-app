/**
 * Created by Yannick Lacaute on 19/01/17.
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
package com.thorpora.core.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.*;

public class RestUtiles {

/*
    protected ResponseEntity<String> doPut(String url, Resource requestBody, Object... pathVariables) {
        url = "http://localhost:" + serverPort + "//" + url;
        Long id = 2l;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body;
        try {
            body = jMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new ServerConfigurationException("Jakson serialization error on PUT", e);
        }
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate
                .exchange(url, HttpMethod.PUT, entity, String.class, pathVariables);
        return response;
    }
*/

}
