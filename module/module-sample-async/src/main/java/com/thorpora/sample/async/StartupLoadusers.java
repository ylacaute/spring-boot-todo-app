/**
 * Created by Yannick Lacaute on 28/12/16.
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
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import javax.inject.Inject;

public class StartupLoadusers {

    private final static Logger logger = LoggerFactory.getLogger(StartupLoadusers.class);

    @Inject
    private GitHubLookupService gitHubLookupService;

    @EventListener(ContextRefreshedEvent.class)
    public void devContextRefreshedEvent() throws Exception {
        logger.info("Loading github users");

        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        //Future<User> page1 = gitHubLookupService.findUser("PivotalSoftware");
        //Future<User> page2 = gitHubLookupService.findUser("CloudFoundry");
        //Future<User> page3 = gitHubLookupService.findUser("Spring-Projects");

        // Wait until they are all done
        //while (!(page1.isDone() && page2.isDone() && page3.isDone())) {
        //    Thread.sleep(10); //10-millisecond pause between each check
       // }

        // Print results, including elapsed time
//        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
//        logger.info("--> " + page1.get());
//        logger.info("--> " + page2.get());
//        logger.info("--> " + page3.get());


    }

}