package com.id.cloud.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class IDCloudDBConfiguration {
	
	@Value("/WEB-INF/cfg-props/dummy.sql")
	private Resource schemaScript;
	
	/**
	 * DataSource Configuration
	 * @return DataSource
	 */
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource dataSource = new org.springframework.jdbc.datasource.DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost/idcloud");
		dataSource.setUsername("root");
		dataSource.setPassword("ljh123");
		return dataSource;
	}
	
	@Bean
	public JdbcTemplate  jdbcTemplate(DataSource dataSource){
		return new JdbcTemplate(dataSource);
	}
	
	@Bean
	public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
	    DataSourceInitializer initializer = new DataSourceInitializer();
	    initializer.setDataSource(dataSource);
	    initializer.setDatabasePopulator(databasePopulator());
	    return initializer;
	}

	@Bean
	public DatabasePopulator databasePopulator() {
	    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
	    populator.addScript(schemaScript);
	    return populator;
	}
	
	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource){
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
		return dataSourceTransactionManager;
	}
	
	@Bean
	public TransactionTemplate transactionTemplate(DataSourceTransactionManager transactionManager){
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.setTimeout(30);
		return transactionTemplate;
	}
	
	/**
	 * hibernateVendor
	 * @return HibernateJpaVendorAdapter
	 */
/*	@Bean
	public HibernateJpaVendorAdapter hibernateVendor(){
		HibernateJpaVendorAdapter hibernateVendor = new org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter();
		hibernateVendor.setDatabase(Database.MYSQL);
		hibernateVendor.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
		hibernateVendor.setShowSql(true);
		hibernateVendor.setGenerateDdl(true);
		return hibernateVendor;
	}*/
	
	/**
	 * JPA Entity Manager Factory
	 * @return LocalContainerEntityManagerFactoryBean
	 */
/*	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = 
				new org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPackagesToScan("com.id.cloud.delicacy.entities");
		entityManagerFactoryBean.setJpaProperties(hibernateProperties());
		entityManagerFactoryBean.setJpaVendorAdapter(hibernateVendor());
		return entityManagerFactoryBean;
	}*/
	
	/**
	 * Transaction Config
	 * @param emf
	 * @return JpaTransactionManager
	 */
/*	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf){
		JpaTransactionManager transactionManager = new org.springframework.orm.jpa.JpaTransactionManager();	
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}*/
	
/*	Properties hibernateProperties() {
	      return new Properties() {
			private static final long serialVersionUID = 2454770249768254988L;

			{
	            setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
	            setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
	            setProperty("hibernate.globally_quoted_identifiers", "true");
	            setProperty("hibernate.hbm2ddl.import_files","/WEB-INF/cfg-props/import.sql");
	            setProperty("hibernate.hbm2ddl.import_files","/WEB-INF/cfg-props/import.sql");
	         }
	      };
	   }*/
	
	/**
	 * SessionFactory Configuration
	 * @return LocalSessionFactoryBean
	 */
	/*@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.id.cloud.delicacy.entities" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}*/
	
	/**
	 * This will ensure that Hibernate or JPA exceptions are automatically 
	 * translated into Spring's generic DataAccessException hierarchy for those
	 * classes annotated with Repository. For example, see ***DAOImpl.
	 * @return PersistenceExceptionTranslationPostProcessor
	 */
/*	@Bean
	public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor(){
		return new org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor();
	}*/
}
