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
package com.thorpora.module.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;


public class ColoredStatus {

    private final static Logger log = LoggerFactory.getLogger(ColoredStatus.class);

    public enum Status {
        INIT(AnsiColor.YELLOW),
        SUCCESS(AnsiColor.GREEN),
        FAILED(AnsiColor.RED);

        public AnsiColor color;

        Status(AnsiColor color) {
            this.color = color;
        }
    }

    public static String getText(Status status, String primaryText) {
        return getText(status, primaryText, null);
    }

    public static String getText(Status status, String primaryText, String secondaryText) {
        return AnsiOutput.encode(AnsiStyle.BOLD)
                + getStatus(status)
                + " "
                + primaryText
                + " "
                + AnsiOutput.encode(AnsiStyle.NORMAL)
                + (secondaryText != null ? "(" + secondaryText + ")" : "");
    }

    private static String getStatus(Status status) {
        return AnsiOutput.encode(AnsiColor.WHITE) + "["
                + AnsiOutput.encode(status.color) + status.name()
                + AnsiOutput.encode(AnsiColor.WHITE) + "]";
    }

}
