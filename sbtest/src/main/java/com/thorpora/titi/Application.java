package com.thorpora.titi; /**
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

import com.thorpora.titi.properties.TitiProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

import javax.persistence.EntityManagerFactory;

/*
@EnableConfigurationProperties
@EnableAutoConfiguration
@Configuration

 */
//@Configuration
/*@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class
})*/
//@ComponentScan(basePackageClasses = AppConfig.class)
//@SpringBootApplication
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .parent(Application.class)
                .web(true)
                .logStartupInfo(true)
                .headless(true)
                .listeners()
                .initializers()
                .run(args);
    }


    /*
    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory(EntityManagerFactory emf) {
        HibernateJpaSessionFactoryBean factory = new HibernateJpaSessionFactoryBean();
        factory.setEntityManagerFactory(emf);
        return factory;
    }*/



}
