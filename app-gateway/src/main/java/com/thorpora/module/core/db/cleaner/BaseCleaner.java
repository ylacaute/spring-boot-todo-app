/**
 * Created by Yannick Lacaute on 24/01/17.
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

import com.thorpora.gateway.core.db.DBCleanerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public abstract class BaseCleaner implements DBCleaner {

    private final static Logger log = LoggerFactory.getLogger(BaseCleaner.class);

    @Inject
    private DataSource dataSource;

    protected abstract void cleanTables(Statement stmt, String[] tables) throws SQLException;

    protected void afterClean(Statement stmt) throws SQLException {
    }

    protected void beforeClean(Statement stmt) throws SQLException {
    }

    public void clean(String... tables) {
        log.debug("Clear tables {}", Arrays.toString(tables));
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Statement stmt = connection.createStatement();
                try {
                    beforeClean(stmt);
                    cleanTables(stmt, tables);
                    afterClean(stmt);
                    connection.commit();
                } finally {
                    stmt.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                connection.rollback();
                throw new DBCleanerException(String
                        .format("Exception during cleanTables (statement) of %s", tables), e);
            }
        } catch (SQLException e) {
            throw new DBCleanerException(String
                    .format("Exception during cleanTables (connection or rollback) of %s", tables), e);
        }
    }

}
