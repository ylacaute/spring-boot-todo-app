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
package com.thorpora.sample.async;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class GitHubLookupService {

    private static final Logger logger = LoggerFactory.getLogger(GitHubLookupService.class);

    private final RestTemplate restTemplate;

    public GitHubLookupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

//    @Async
//    public Future<User> findUser(String com.thorpora.user) throws InterruptedException {
//        logger.info("Looking up " + com.thorpora.user);
//        String url = String.format("https://api.github.com/users/%s", com.thorpora.user);
//        User results = restTemplate.getForObject(url, User.class);
//        // Artificial delay of 1s for demonstration purposes
//        Thread.sleep(1000L);
//        return new AsyncResult<>(results);
//    }

}