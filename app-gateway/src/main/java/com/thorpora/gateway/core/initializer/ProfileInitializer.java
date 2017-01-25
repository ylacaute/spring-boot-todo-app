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
package com.thorpora.gateway.core.initializer;

import com.thorpora.gateway.core.AppProfiles;
import com.thorpora.module.core.exception.ProfileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ProfileInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private final static Logger logger = LoggerFactory.getLogger(ProfileInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        //ConfigurableEnvironment env = applicationContext.getEnvironment();
        //Set<String> profiles = getPrimaryProfiles(env.getActiveProfiles());
        //String[] activeProfiles = env.getActiveProfiles();

//        if (profiles.isEmpty())
//            throw new ProfileException("No primary profile set");
//        if (profiles.size() > 1)
//            throw new ProfileException("There can be only one primary active profile");

        /*
        String primaryProfile = profiles.iterator().next();
        String[] dependencies = AppProfiles
                .dependenciesOf(primaryProfile, env.getActiveProfiles());

        Arrays.stream(dependencies).forEach(env::addActiveProfile);
        logger.info("Initialize {} profile (dependencies: {})", primaryProfile, dependencies);
        */
    }

    private Set<String> getPrimaryProfiles(String[] activeProfiles) {


        return Arrays
                .stream(activeProfiles)
                .filter(AppProfiles.PRIMARY_PROFILES::contains)
                .collect(Collectors.toSet());
    }

}
