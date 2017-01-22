/**
 * Created by Yannick Lacaute on 21/01/17.
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
package com.thorpora.test.rest;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FailSafeLoggerListener extends RunListener {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String COM_THORPORA = "com.thorpora";


    private void logTestStart(String className, String methodName, Object[] parameters) {
        // display tests parameters for mirakl-web only because :
        // 1. Several mirakl-web DataProviders contain hundreds of tests cases
        // 2. Parameters are short and readable in the STDOUT
        if (className.startsWith(COM_THORPORA)) {
            logger.info("Running {}.{} with {}", className, methodName, parameters);
        } else {
            logger.info("Running {}.{}", className, methodName);
        }
    }

    private void logTestFailure(String className, String methodName) {
        logger.error("Failure on {}.{}", className, methodName);
    }

    private void logTestSkipped(String className, String methodName) {
        logger.error("Skipped Test on {}.{} - This test has to be fixed or removed quickly.", className, methodName);
    }

    /**
     * Called before any tests have been run. This may be called on an
     * arbitrary thread.
     *
     * @param description describes the tests to be run
     */
    public void testRunStarted(Description description) throws Exception {
    }

    /**
     * Called when all tests have finished. This may be called on an
     * arbitrary thread.
     *
     * @param result the summary of the test run, including all the tests that failed
     */
    public void testRunFinished(Result result) throws Exception {
    }

    /**
     * Called when an atomic test is about to be started.
     *
     * @param description the description of the test that is about to be run
     * (generally a class and method name)
     */
    public void testStarted(Description description) throws Exception {
        logTestStart(description.getClassName(), description.getMethodName(), null);
    }

    /**
     * Called when an atomic test has finished, whether the test succeeds or fails.
     *
     * @param description the description of the test that just ran
     */
    public void testFinished(Description description) throws Exception {
    }

    /**
     * Called when an atomic test fails, or when a listener throws an exception.
     *
     * <p>In the case of a failure of an atomic test, this method will be called
     * with the same {@code Description} passed to
     * {@link #testStarted(Description)}, from the same thread that called
     * {@link #testStarted(Description)}.
     *
     * <p>In the case of a listener throwing an exception, this will be called with
     * a {@code Description} of {@link Description#TEST_MECHANISM}, and may be called
     * on an arbitrary thread.
     *
     * @param failure describes the test that failed and the exception that was thrown
     */
    public void testFailure(Failure failure) throws Exception {
        logTestFailure(failure.getDescription().getClassName(), failure.getDescription().getMethodName());
    }

    /**
     * Called when an atomic test flags that it assumes a condition that is
     * false
     *
     * @param failure describes the test that failed and the
     * {@link org.junit.AssumptionViolatedException} that was thrown
     */
    public void testAssumptionFailure(Failure failure) {
    }

    /**
     * Called when a test will not be run, generally because a test method is annotated
     * with {@link org.junit.Ignore}.
     *
     * @param description describes the test that will not be run
     */
    public void testIgnored(Description description) throws Exception {
        logTestSkipped(description.getClassName(), description.getMethodName());
    }


}
