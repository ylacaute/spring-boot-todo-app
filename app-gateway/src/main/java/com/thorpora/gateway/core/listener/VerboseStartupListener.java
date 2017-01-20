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
package com.thorpora.gateway.core.listener;

import com.thorpora.gateway.core.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.io.StringWriter;

public class VerboseStartupListener {

    private final static Logger logger = LoggerFactory.getLogger(VerboseStartupListener.class);

    private final static String WILDCARDS = "********************************";

    @Inject
    private AppProperties appProperties;

    @Inject
    private HttpMessageConverters httpConverters;

    @EventListener
    public void onApplicationEvent(ApplicationEvent event) {
        logger.info("Application event : {}", event.getClass().getSimpleName());

        if (event.getClass().isAssignableFrom(ApplicationReadyEvent.class)) {
            printHttpConverters();
            printDBStatus();
            printProperties();
        }
    }

    private void printHttpConverters() {
        logger.debug("HttpConverters : {}", httpConverters.getConverters().toString());
    }

    public void configureHttpMessageConverters() {
        //httpConverters.getConverters();
    }

    private void printDBStatus() {


        StringWriter overview = new StringWriter();
        PrintWriter printer = new PrintWriter(overview);

        printer.println();
        printer.printf("%s%s", WILDCARDS, WILDCARDS);
        printer.println();
        printer.printf("DB Profile : %s", "profile");
        printer.println();
        printer.printf(" - Tables : %s", "1");
        printer.println();
        printer.printf("%s%s", WILDCARDS, WILDCARDS);
        printer.println();

        System.out.println(overview.toString());
    }



    private void printProperties() {
        StringWriter overview = new StringWriter();
        PrintWriter printer = new PrintWriter(overview);

        printer.println();
        printer.printf("%s%s", WILDCARDS, WILDCARDS);
        printer.println();
        printer.printf("Application property app name: %s", appProperties.getName());
        printer.println();
        printer.printf("Application property app description: %s", appProperties.getDescription());
        //printer.println();
        //printer.printf("Application property via class (name) : %s", titiProperties.getFirstName());
        printer.println();
        printer.printf("%s%s", WILDCARDS, WILDCARDS);
        printer.println();

        System.out.println(overview.toString());
    }
}