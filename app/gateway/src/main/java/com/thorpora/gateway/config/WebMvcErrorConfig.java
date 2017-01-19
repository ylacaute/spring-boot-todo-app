/**
 * Created by Yannick Lacaute on 29/12/16.
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
package com.thorpora.gateway.config;

import com.thorpora.module.core.error.ErrorController;
import com.thorpora.module.core.error.ErrorLogger;
import com.thorpora.gateway.web.ExceptionHandlerController;
import org.slf4j.event.Level;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.persistence.EntityNotFoundException;

@Configuration
public class WebMvcErrorConfig {

    @Bean
    public ExceptionHandlerController exceptionHandlerController(ErrorController errorController) {
        return new ExceptionHandlerController(errorController);
    }

    @Primary
    @Bean
    public ErrorController ErrorController(ErrorLogger errorLogger, ErrorAttributes errorAttributes) {
        return new ErrorController(errorLogger, errorAttributes);
    }

    @Bean
    public ErrorLogger errorLogger() {
        ErrorLogger errorLogger = new ErrorLogger();
        errorLogger.mapWithStack(Level.INFO, EntityNotFoundException.class);
        return errorLogger;
    }
}
