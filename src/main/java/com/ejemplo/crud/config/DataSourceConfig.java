// src/main/java/com/ejemplo/crud/config/DataSourceConfig.java
package com.ejemplo.crud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Value("${spring.datasource.username}")
    private String dataSourceUsername;

    @Value("${spring.datasource.password:}")
    private String dataSourcePassword;

    @Bean
    public DataSource dataSource(AwsConfig awsConfig) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(dataSourceUrl);
        dataSource.setUsername(dataSourceUsername);
        
        // Usar Secrets Manager si no hay password en properties
        if (dataSourcePassword.isEmpty()) {
            dataSource.setPassword(awsConfig.getRdsSecret());
        } else {
            dataSource.setPassword(dataSourcePassword);
        }
        
        return dataSource;
    }
}
