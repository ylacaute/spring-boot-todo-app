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
package com.thorpora.gateway.core.config;

import com.thorpora.gateway.core.AppProfiles;
import com.thorpora.gateway.core.AppProperties;
import com.thorpora.gateway.core.listener.StartupListener;
import com.thorpora.gateway.core.listener.VerboseStartupListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class CoreConfig {

    private final static Logger logger = LoggerFactory.getLogger(CoreConfig.class);

    @Bean
    @ConfigurationProperties(prefix = "spring.application")
    public AppProperties appProperties() {
        return new AppProperties();
    }

    @Bean
    @Profile(AppProfiles.DEBUG)
    public VerboseStartupListener verboseStartupListener() {
        return new VerboseStartupListener();
    }

    @Bean
    @Profile(AppProfiles.PRODUCTION)
    public StartupListener startupListener() {
        return new StartupListener();
    }

    // test jenkins hook 8
}
