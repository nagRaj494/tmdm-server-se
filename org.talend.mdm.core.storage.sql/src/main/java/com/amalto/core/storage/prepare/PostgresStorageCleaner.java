/*
 * Copyright (C) 2006-2016 Talend Inc. - www.talend.com
 *
 * This source code is available under agreement available at
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
 *
 * You should have received a copy of the agreement
 * along with this program; if not, write to Talend SA
 * 9 rue Pages 92150 Suresnes, France
 */

package com.amalto.core.storage.prepare;

import com.amalto.core.storage.Storage;
import com.amalto.core.storage.datasource.DataSource;
import com.amalto.core.storage.datasource.RDBMSDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

class PostgresStorageCleaner implements StorageCleaner {

    private static final Logger LOGGER = Logger.getLogger(PostgresStorageCleaner.class);

    private final StorageCleaner next;

    PostgresStorageCleaner(StorageCleaner next) {
        this.next = next;
    }

    public void clean(Storage storage) {
        try {
            DataSource storageDataSource = storage.getDataSource();
            if (!(storageDataSource instanceof RDBMSDataSource)) {
                throw new IllegalArgumentException("Storage to clean does not seem to be a RDBMS storage.");
            }

            RDBMSDataSource dataSource = (RDBMSDataSource) storageDataSource;
            if (!dataSource.hasInit()) {
                throw new IllegalArgumentException("Data source '" + dataSource.getName() + "' does not define initialization information.");
            }

            Driver driver = (Driver) Class.forName(dataSource.getDriverClassName()).newInstance();
            Connection connection = driver.connect(dataSource.getInitConnectionURL() + "?user=" + dataSource.getInitUserName() + "&password=" + dataSource.getInitPassword(), new Properties());  //$NON-NLS-1$ //$NON-NLS-2$
            try {
                Statement statement = connection.createStatement();
                try {
                    statement.execute("DROP DATABASE " + dataSource.getDatabaseName() + ";");  //$NON-NLS-1$ //$NON-NLS-2$
                } catch (SQLException e) {
                    // Assumes database is already dropped.
                    LOGGER.debug("Exception occurred during DROP DATABASE statement.", e);
                } finally {
                    statement.close();
                }
            } finally {
                connection.close();
            }

            next.clean(storage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
