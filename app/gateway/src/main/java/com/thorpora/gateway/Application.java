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
package com.thorpora.gateway;


import com.thorpora.gateway.config.CoreConfig;
import com.thorpora.gateway.db.DBConfig;
import com.thorpora.module.mail.config.MailConfig;
import com.thorpora.module.todo.config.TodoConfig;
import com.thorpora.module.user.config.UserConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * We don't use @SpringBootApplication because of its default componentScan of the whole project. Remember that
 * @SpringBootApplication is stricly equivalent to @Configuration, @EnableAutoConfiguration and @ComponentScan.
 *
 * We basicaly do the same but with a single directory scan, we bring better startup performance and
 * a better overview of which beans are loaded.
 *
 */
@EnableConfigurationProperties
@EnableAutoConfiguration
@EntityScan(basePackages = "com.thorpora.module")
@EnableJpaRepositories(basePackages = "com.thorpora.module")
@ComponentScan(basePackageClasses = CoreConfig.class)
@Import({
        DBConfig.class,
        UserConfig.class,
        MailConfig.class,
        TodoConfig.class
        //AsyncConfig.class
})
public class Application {

    private final static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        new SpringApplicationBuilder()
                .parent(Application.class)
                .web(true)
                .logStartupInfo(true)
                .headless(true)
                .listeners()
                .initializers() // new ProfileInitializer()
                .run(args);
    }


}
