package com.stackroute.keepmovie.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/*This class will contain the application-context for the application. 
 * Define the following annotations:
 * @Configuration - Annotating a class with the @Configuration indicates that the 
 *                  class can be used by the Spring IoC container as a source of 
 *                  bean definitions
 * @EnableTransactionManagement - Enables Spring's annotation-driven transaction management capability.
 *                  
 * */

@Configuration
@EnableTransactionManagement
@EnableWebMvc
@ComponentScan("com.stackroute.keepmovie.*")
public class ApplicationContextConfig {

	/*
	 * Define the bean for DataSource. In our application, we are using MySQL as the
	 * dataSource. To create the DataSource bean, we need to know: 1. Driver class
	 * name 2. Database URL 3. UserName 4. Password
	 */

	@Bean
	public DataSource dataSource() {
		// configure and return the necessary JDBC DataSource
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/Movies");
		dataSource.setUsername("root");
		dataSource.setPassword("root123");
		return dataSource;
	}

	/*
	 * Define the bean for SessionFactory. Hibernate SessionFactory is the factory
	 * class through which we get sessions and perform database operations.
	 */
	@Bean
	@Autowired
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
		LocalSessionFactoryBean localSessionFactory = new LocalSessionFactoryBean();
		localSessionFactory.setDataSource(dataSource);
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		properties.put("hibernate.hbm2ddl.auto", "create");
		localSessionFactory.setHibernateProperties(properties);
		localSessionFactory.setPackagesToScan("com.stackroute.keepmovie.model");

		return localSessionFactory;

	}

	/*
	 * Define the bean for Transaction Manager. HibernateTransactionManager handles
	 * transaction in Spring. The application that uses single hibernate session
	 * factory for database transaction has good choice to use
	 * HibernateTransactionManager. HibernateTransactionManager can work with plain
	 * JDBC too. HibernateTransactionManager allows bulk update and bulk insert and
	 * ensures data integrity.
	 */

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txnManager = new HibernateTransactionManager();
		txnManager.setSessionFactory(sessionFactory);
		return txnManager;
	}

}
