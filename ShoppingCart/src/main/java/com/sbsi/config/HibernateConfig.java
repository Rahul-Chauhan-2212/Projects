package com.sbsi.config;

import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("resources/config/database.properties")
@EnableTransactionManagement
@ComponentScan(basePackages = "com.sbsi.config")
public class HibernateConfig {

	@Autowired
	private Environment env;

	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("db.driverClassName"));
		dataSource.setUrl(env.getProperty("db.url"));
		dataSource.setUsername(env.getProperty("db.username"));
		dataSource.setPassword(env.getProperty("db.password"));
		return dataSource;

	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setHibernateProperties(getProperties());
		factoryBean.setPackagesToScan(env.getProperty("db.packageToScan"));
		return factoryBean;

	}

	public Properties getProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", env.getProperty("db.dialect"));
		properties.setProperty("hibernate.show_sql", env.getProperty("db.show-sql"));
		properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("db.ddl-auto"));
		properties.setProperty("current_session_context_class", env.getProperty("db.session-context"));
		properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
		return properties;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory().getObject());
		txManager.setDataSource(dataSource());
		return txManager;
	}

	@Bean
	public HibernateTemplate hibernateTemplate() {
		return new HibernateTemplate(sessionFactory().getObject());
	}

}
