/**
 * Created by Yannick Lacaute on 31/12/16.
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
package com.thorpora.gateway.core.db;

import org.postgresql.util.PSQLException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

import java.io.PrintWriter;
import java.io.StringWriter;

public class DBConfigFailureAnalyzer extends AbstractFailureAnalyzer<PSQLException> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, PSQLException cause) {
        return new FailureAnalysis(getDescription(cause), getAction(cause), cause);
    }

    private String getDescription(PSQLException ex) {
        StringWriter description = new StringWriter();
        PrintWriter printer = new PrintWriter(description);
        printer.printf("Impossible to connect to the database.");
        printer.println();
        return description.toString();
    }

    private String getAction(PSQLException ex) {
        StringWriter action = new StringWriter();
        PrintWriter printer = new PrintWriter(action);
        printer.printf("Please verify you have started your database.");
        printer.println();
        printer.printf(ex.getMessage());
        printer.println();
        printer.printf("Example : systemctl start postgresql.service");
        printer.println();

        return action.toString();
    }

}
