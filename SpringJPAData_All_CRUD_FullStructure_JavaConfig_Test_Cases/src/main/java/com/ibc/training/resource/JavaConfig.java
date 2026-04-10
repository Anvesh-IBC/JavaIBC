package com.ibc.training.resource;

import javax.persistence.EntityManagerFactory;

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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:cst_conn.properties")
@EnableJpaRepositories(basePackages = "com.ibc.training.dao", transactionManagerRef = "txManager", entityManagerFactoryRef = "cst_entityManagerFactory")
// for java configuration it is mandatory to give name of the entityManagerFactory 
//if default name is not given. Default name is:entityManagerFactory
@ComponentScan({"com.ibc.training.dao","com.ibc.training.service"})
public class JavaConfig {

	// reading value from properties file and giving to the datasource
	@Value("${cst_db_driver}")
	private String driverName;
	
	@Value("${cst_db_url}")
	private String url;
	
	@Value("${cst_user}")
	private String userName;
	
	@Value("${cst_password}")
	private String password;
	//To resolve ${} in @Value
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
			return new PropertySourcesPlaceholderConfigurer();
	}

	//Data source can have any name
	@Bean(name = "cst_DataSource")
	public DriverManagerDataSource getDataSource() {
		System.out.println(driverName);
		System.out.println(url);
		System.out.println(userName);
		System.out.println(password);
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverName);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);

		return dataSource;
	}

	public HibernateJpaVendorAdapter getVendorAdaptor(){
		HibernateJpaVendorAdapter adapter = new  HibernateJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setGenerateDdl(false);
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
		return adapter;
	}

	@Bean(name = "cst_entityManagerFactory") //can have any name
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(DriverManagerDataSource dataSource) {
		LocalContainerEntityManagerFactoryBean factoryBuilder = new LocalContainerEntityManagerFactoryBean();
		factoryBuilder.setDataSource(dataSource);
		factoryBuilder.setJpaVendorAdapter(getVendorAdaptor());
		factoryBuilder.setPackagesToScan("com.ibc.training.entity");
		return factoryBuilder;
	}
	@Bean(name="txManager")
	  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
	    JpaTransactionManager txManager = new JpaTransactionManager();
	    txManager.setEntityManagerFactory(entityManagerFactory);
	    return txManager;
	  }

}
