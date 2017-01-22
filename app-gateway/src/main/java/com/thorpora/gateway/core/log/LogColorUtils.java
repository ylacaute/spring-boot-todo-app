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
package com.thorpora.gateway.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Note : don't worry about all '+', compiler optimize it.
 */
public class LogColorUtils {

    private final static Logger log = LoggerFactory.getLogger(LogColorUtils.class);

    public enum Status {
        INIT(AnsiColor.YELLOW),
        SUCCESS(AnsiColor.GREEN),
        FAILED(AnsiColor.RED);

        public String color;

        Status(String color) {
            this.color = color;
        }
    }

    public static class AnsiColor {
        static String BOLD = "\u001B[1m";
        static String RESET = "\u001B[0m";
        static String BLACK = "\u001B[30m";
        static String RED = "\u001B[31m";
        static String GREEN = "\u001B[32m";
        static String YELLOW = "\u001B[33m";
        static String BLUE = "\u001B[34m";
        static String PURPLE = "\u001B[35m";
        static String CYAN = "\u001B[36m";
        static String WHITE = "\u001B[37m";
    }

    public static void logStatus(Status status, String primaryText) {
        logStatus(status, primaryText, null);
    }

    public static void logStatus(Status status, String primaryText, String secondaryText) {
        String text = AnsiColor.BOLD
                + getStatus(status)
                + " "
                + primaryText
                + " "
                + (secondaryText != null ? "(" + secondaryText + ")" : "")
                + AnsiColor.RESET;
        log.info(text);
    }

    private static String getStatus(Status status) {
        return AnsiColor.WHITE
                + "[" + status.color + status.name()
                + AnsiColor.WHITE + "]";
    }


}
