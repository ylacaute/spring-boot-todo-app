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
package com.thorpora.gateway.core.analyzer;

import com.thorpora.gateway.core.AppProfiles;
import com.thorpora.module.core.exception.ProfileException;
import com.thorpora.module.core.exception.ServerConfigurationException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StartupFailureAnalyzer extends AbstractFailureAnalyzer<ServerConfigurationException> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, ServerConfigurationException cause) {
        return new FailureAnalysis(getDescription(cause), getAction(cause), cause);
    }

    private String getDescription(ServerConfigurationException ex) {
        StringWriter description = new StringWriter();
        PrintWriter printer = new PrintWriter(description);
        printer.printf("There is configuration problem with your Spring Boot configuration. ");
        if (ex instanceof ProfileException) {
            printer.printf("You have a profile configuration problem : %s", ex.getMessage());
        }
        return description.toString();
    }

    private String getAction(ServerConfigurationException ex) {
        StringWriter action = new StringWriter();
        PrintWriter printer = new PrintWriter(action);
        printer.printf("Please verify your configuration. ");
        if (ex instanceof ProfileException) {
            printer.printf("You must define an active profile (and only one) from : %s", AppProfiles.PRIMARY_PROFILES);
            printer.println();
            printer.printf("Sample vm arg : -Dspring.profiles.active=dev");
        }
        return action.toString();
    }


}
