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
package com.thorpora.module.core.db.cleaner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Statement;

public class H2Cleaner extends BaseCleaner {

    private final static Logger log = LoggerFactory.getLogger(H2Cleaner.class);

    @Override
    protected void cleanTables(Statement stmt, String[] tables) throws SQLException {
        for (String table : tables) {
            stmt.execute("TRUNCATE TABLE " + table);
        }
    }

    @Override
    protected void beforeClean(Statement stmt) throws SQLException {
        stmt.execute("SET REFERENTIAL_INTEGRITY FALSE");
    }

    @Override
    protected void afterClean(Statement stmt) throws SQLException {
        stmt.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }
}
