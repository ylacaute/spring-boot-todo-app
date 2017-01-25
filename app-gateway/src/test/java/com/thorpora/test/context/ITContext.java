package com.thorpora.test.context; /**
 * Created by Yannick Lacaute on 27/12/16.
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

import com.thorpora.gateway.core.AppProfiles;
import com.thorpora.gateway.core.config.CoreConfig;
import com.thorpora.gateway.core.initializer.ProfileInitializer;
import com.thorpora.module.core.log.ColoredStatus;
import com.thorpora.module.core.db.cleaner.DBCleaner;
import com.thorpora.module.core.db.cleaner.H2Cleaner;
import com.thorpora.module.core.db.cleaner.PostgresCleaner;
import com.thorpora.module.mail.config.MailConfig;
import com.thorpora.module.todo.config.TodoConfig;
import com.thorpora.module.user.config.UserConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static com.thorpora.module.core.log.ColoredStatus.Status.INIT;

@Configuration
@EnableConfigurationProperties
@EnableAutoConfiguration
@EntityScan(basePackages = "com.thorpora.module")
@EnableJpaRepositories(basePackages = "com.thorpora.module")
@ComponentScan(basePackageClasses = CoreConfig.class)
@Import({
        UserConfig.class,
        MailConfig.class,
        TodoConfig.class
})
    public class ITContext {

    private final static Logger log = LoggerFactory.getLogger(ITContext.class);

    public ITContext() {
        ColoredStatus.getText(INIT, "Using ITContext configuration");
    }


    @Profile(AppProfiles.DB_H2_MEM)
    @Bean
    public DBCleaner h2Cleaner() {
        log.info(ColoredStatus.getText(INIT, "H2 Cleaner"));
        return new H2Cleaner();
    }

    @Profile(AppProfiles.DB_POSTGRESQL)
    @Bean
    public DBCleaner postgresCleaner() {
        log.info(ColoredStatus.getText(INIT, "Postgres Cleaner"));
        return new PostgresCleaner();
    }
}
