package com.rest.api.config;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.rest.api.service","com.rest.api.dao" })
@PropertySource("classpath:system.properties")
public class SpringApplicationConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

		Properties props = new Properties();

		// Setting JDBC properties
		props.put("hibernate.connection.driver_class", env.getProperty("mysql.driver"));
		props.put("hibernate.connection.url", env.getProperty("mysql.jdbcUrl"));
		props.put("hibernate.connection.username", env.getProperty("mysql.username"));
		props.put("hibernate.connection.password", env.getProperty("mysql.password"));

		// Setting Hibernate properties
		props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

		// Setting C3P0 properties
		props.put("hibernate.c3p0.min_size", env.getProperty("hibernate.c3p0.min_size"));
		props.put("hibernate.c3p0.max_size", env.getProperty("hibernate.c3p0.max_size"));
		props.put("hibernate.c3p0.max_size", env.getProperty("hibernate.c3p0.acquire_increment"));
		props.put("hibernate.c3p0.timeout", env.getProperty("hibernate.c3p0.timeout"));
		props.put("hibernate.c3p0.max_statements", env.getProperty("hibernate.c3p0.max_statements"));

		factoryBean.setHibernateProperties(props);
		factoryBean.setPackagesToScan("com.rest.api.models");

		return factoryBean;
	}

	@Bean
	public HibernateTransactionManager getTransactionManager() {
		
		LocalSessionFactoryBean factoryBean =getSessionFactory();
		SessionFactory sessionFactory=factoryBean.getObject();
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		return transactionManager;
	}
}
