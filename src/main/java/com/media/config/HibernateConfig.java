package com.media.config;

import java.util.Properties;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import ch.qos.logback.classic.Logger;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@PropertySource(value = { "classpath:hibernate.properties" })
public class HibernateConfig {

	private static final Logger LOGGER = (Logger)LoggerFactory.getLogger(HibernateConfig.class);

	@Value("${db.driverClassName}")
	private String dbDriverClassName;
	@Value("${db.url}")
	private String dbUrl;
	@Value("${db.username}")
	private String dbUsername;
	@Value("${db.password}")
	private String dbPassword;
	@Value("${db.min.connections.per.partition}")
	private String dbMinConnectionsPerPartition;
	@Value("${db.max.connections.per.partition}")
	private String dbMaxConnectionsPerPartition;
	
	@Value("${hibernate.dialect}")
	private String dialect;
	@Value("${hibernate.max_fetch_depth}")
	private String maxFetchDepth;
	@Value("${hibernate.show_sql}")
	private String showSql;
	@Value("${hibernate.format_sql}")
	private String formatSql;
	@Value("${hibernate.prepare_sql}")
	private String prepareSql;
	@Value("${hibernate.hbm2ddl.auto}")
	private String hbm2ddlAuto;
	@Value("${hibernate.jdbc.fetch_size}")
	private String fetchSize;
	@Value("${hibernate.jdbc.batch_size}")
	private String batchSize;
	@Value("${hibernate.generate_statistics}")
	private String generateStatistics;
	@Value("${hibernate.cache.use_second_level_cache}")
	private String useSecondLevelCache;
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com.media.entity");
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", dialect);
		hibernateProperties.setProperty("hibernate.max_fetch_depth", maxFetchDepth);
		hibernateProperties.setProperty("hibernate.show_sql", showSql);
		hibernateProperties.setProperty("hibernate.format_sql", formatSql);
		hibernateProperties.setProperty("hibernate.prepare_sql", prepareSql);
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", hbm2ddlAuto);
		hibernateProperties.setProperty("hibernate.jdbc.fetch_size", fetchSize);
		hibernateProperties.setProperty("hibernate.jdbc.batch_size", batchSize);
		hibernateProperties.setProperty("hibernate.generate_statistics", generateStatistics);
		hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", useSecondLevelCache);
		sessionFactory.setHibernateProperties(hibernateProperties);
		return sessionFactory;
	}

	public ComboPooledDataSource dataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(dbDriverClassName);
			dataSource.setJdbcUrl(dbUrl);
			dataSource.setUser(dbUsername);
			dataSource.setPassword(dbPassword);
			dataSource.setMinPoolSize(Integer.valueOf(dbMinConnectionsPerPartition));
			dataSource.setMaxPoolSize(Integer.valueOf(dbMaxConnectionsPerPartition));
		} catch (Exception e) {
			LOGGER.warn("Exception occurred : ", e);
		}
		return dataSource;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

}
