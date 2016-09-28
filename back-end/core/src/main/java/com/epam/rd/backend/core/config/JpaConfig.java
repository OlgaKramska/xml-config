package com.epam.rd.backend.core.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

//@Configuration
//@EnableJpaRepositories("com.epam.rd.backend.core.repository")
//@ComponentScan("com.epam.rd.backend.core.service")
//@PropertySource(value = "classpath:app-config.properties")
public class JpaConfig {
//    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
//    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
//    private static final String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
//    private static final String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

//    @Value("${db.driver}")
//    private String jdbcDriverClass;
//    @Value("${db.url}")
//    private String databaseUrlConnection;
//    @Value("${db.username}")
//    private String databaseUsername;
//    @Value("${db.password}")
//    private String databasePassword;
//    @Value("${hibernate.dialect}")
//    private String hibernateDialect;
//    @Value("${hibernate.show_sql}")
//    private String hibernateShowSql;
//    @Value("${hibernate.format_sql}")
//    private String hibernateFormatSql;
//    @Value("${hibernate.hbm2ddl.auto}")
//    private String hibernateHbm2ddlAuto;

//    @Bean
//    static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }

//    @Bean
//    public JpaTransactionManager transactionManager() {
//        final JpaTransactionManager txManager = new JpaTransactionManager();
//        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
//        return txManager;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setDataSource(dataSource());
//        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
//        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        factory.setJpaProperties(hibernateProperties());
//        factory.setPackagesToScan("com.epam.rd.backend.core.model");
//        return factory;
//    }

//    @Bean
//    public DataSource dataSource() {
//        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(jdbcDriverClass);
//        dataSource.setUrl(databaseUrlConnection);
//        dataSource.setUsername(databaseUsername);
//        dataSource.setPassword(databasePassword);
//        return dataSource;
//    }

//    private Properties hibernateProperties() {
//        final Properties properties = new Properties();
//        properties.put(HIBERNATE_DIALECT, hibernateDialect);
//        properties.put(HIBERNATE_SHOW_SQL, hibernateShowSql);
//        properties.put(HIBERNATE_FORMAT_SQL, hibernateFormatSql);
//        properties.put(HIBERNATE_HBM2DDL_AUTO, hibernateHbm2ddlAuto);
//        return properties;
//    }
}
