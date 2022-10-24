package org.modsen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class HibernateConfig {
    private static final String DATASOURCE_URL = "hibernate.connection.url";
    private static final String DATASOURCE_DRIVER = "hibernate.connection.driver_class";
    private static final String DATASOURCE_USERNAME = "hibernate.connection.username";
    private static final String DATASOURCE_PASSWORD = "hibernate.connection.password";
    private static final String DEFAULT_SCHEMA = "hibernate.default_schema";
    @Value("${persistence-unit}")
    private String persistenceUnitName;
    @Autowired
    private Environment env;

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(DATASOURCE_URL, env.getProperty(DATASOURCE_URL));
        props.put(DATASOURCE_DRIVER, env.getProperty(DATASOURCE_DRIVER));
        props.put(DATASOURCE_USERNAME, env.getProperty(DATASOURCE_USERNAME));
        props.put(DATASOURCE_PASSWORD, env.getProperty(DATASOURCE_PASSWORD));
        props.put(DEFAULT_SCHEMA, env.getProperty(DEFAULT_SCHEMA));
        return Persistence.createEntityManagerFactory(persistenceUnitName, props);
    }
}
