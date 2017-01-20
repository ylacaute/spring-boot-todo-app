package com.thorpora.test; /**
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

import com.thorpora.gateway.core.config.CoreConfig;
import com.thorpora.gateway.core.db.DBCleaner;
import com.thorpora.gateway.core.log.LogColorUtils;
import com.thorpora.module.mail.config.MailConfig;
import com.thorpora.module.todo.config.TodoConfig;
import com.thorpora.module.user.config.UserConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

/**
 * Any specific bean to integration test can be defined here?
 */

@Configuration
@EnableConfigurationProperties
@EnableAutoConfiguration
        /*(exclude = {
        DataSourceAutoConfiguration.class
})*/
@EntityScan(basePackages = "com.thorpora.module")
@EnableJpaRepositories(basePackages = "com.thorpora.module")
//@ComponentScan("")
@ComponentScan(basePackageClasses = CoreConfig.class)
@Import({
        //DBConfig.class,
        UserConfig.class,
        MailConfig.class,
        TodoConfig.class
})
@TestPropertySource(locations="classpath:application-test.properties")
public class ITContext {

    @Bean
    public DBCleaner dbCleaner(DataSource dataSource) {
        return new DBCleaner();
    }

    private final static Logger log = LoggerFactory.getLogger(ITContext.class);

    public ITContext() {
        //LogColorUtils.logStatus(LogColorUtils.Status.INIT, "Using ITContext");
    }
}
