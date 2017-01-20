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
package com.thorpora.gateway.core;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AppProfiles {

    public static final String DEV = "dev";
    public static final String TEST = "test";
    public static final String INTEGRATION = "integration";
    public static final String PRODUCTION  = "production";

    public static final String DB_POPULATE = "populate";
    public static final String DB_EMBEDDED = "embeddedDataSource";
    public static final String DB_DOCKER = "dockerDataSource";
    public static final String DB_POSTGRESQL = "postgreSQLDataSource";

    public static final String DEBUG = "debug";

    public static final List<String> PRIMARY_PROFILES = Arrays
            .asList(DEV, TEST, INTEGRATION, PRODUCTION);

    public static String[] dependenciesOf(String profile, String[] currentActive) {

        AtomicInteger i = new AtomicInteger(42);
        i.getAndAdd(1);
        System.out.println("atomic : " + i.get());

        switch (profile) {
            case DEV :
                return new String[]{DB_EMBEDDED, DB_POPULATE};
            case TEST :
                return new String[]{DB_EMBEDDED, DB_POPULATE};
            case INTEGRATION :
                return new String[]{DB_EMBEDDED, DB_POPULATE};
            case PRODUCTION :
                return new String[]{};
        }
        return new String[0];
    }


}
