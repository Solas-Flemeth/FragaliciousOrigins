package org.originsreborn.fragaliciousorigins.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.originsreborn.fragaliciousorigins.configs.MainConfig;

import javax.sql.DataSource;

public class DataSourceManager {
    private static DataSource dataSource;
    public DataSourceManager() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(MainConfig.getJdbcUrl());
        config.setUsername(MainConfig.getUSERNAME());
        config.setPassword(MainConfig.getPASSWORD());
        config.setMaximumPoolSize(5);
        config.setConnectionTimeout(10000L);
        config.setMaxLifetime(250000L);
        dataSource = new HikariDataSource(config);
        OriginsDAO.createTable();
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
