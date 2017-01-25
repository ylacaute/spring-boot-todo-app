/**
 * Created by Yannick Lacaute on 20/01/17.
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
package com.thorpora.test.junit;

import com.thorpora.module.core.log.ColoredStatus;
import com.thorpora.module.core.log.ColoredStatus.Status;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiOutput;

import java.io.FileReader;
import java.util.Properties;

public class TestDecorator extends TestWatcher {

    private final static Logger log = LoggerFactory.getLogger(TestDecorator.class);

    private final static String LB = System.getProperty("line.separator");

    private static Boolean forceAnsi;

    public TestDecorator() {
        if (forceAnsi == null) {
            initForceAnsi();
        }
    }

    protected void succeeded(Description description) {
        printTestStatus(Status.SUCCESS, description, false);
    }

    protected void failed(Throwable e, Description description) {
        printTestStatus(Status.FAILED, description, true);
    }

    protected void printTestStatus(Status status, Description desc, boolean testFail) {
        String className = desc.getClassName()
                .substring(desc.getClassName().lastIndexOf(".") + 1) + ")";
        log.info(ColoredStatus.getText(status, desc.getMethodName(), className));
    }

    private void initForceAnsi() {
        try {
            Properties p = new Properties();
            p.load(new FileReader(Thread.currentThread().getContextClassLoader()
                    .getResource("application.properties").getFile()));
            forceAnsi = Boolean.valueOf(p.getProperty("logging.force.ansi"));
            if (forceAnsi) {
                log.info("forceAnsi option enabled");
                AnsiOutput.setConsoleAvailable(true);
            }
        } catch (Exception e) {
            forceAnsi = false;
            log.warn("Exception during TestDecorator configuration", e);
        }
    }

}
