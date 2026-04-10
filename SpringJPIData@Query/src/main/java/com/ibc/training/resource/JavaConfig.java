package com.ibc.training.resource;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement // ---> <tx:annotation-driven/>
@EnableJpaRepositories(basePackages = "com.ibc.training.dao", transactionManagerRef = "txManager", entityManagerFactoryRef = "cst_entityManagerFactory")
// for java configuration it is mandatory to give name of the entityManagerFactory 
//if default name is not given. Default name is:entityManagerFactory
@ComponentScan({"com.ibc.training.dao","com.ibc.training.service"})
public class JavaConfig {

	@Bean(name = "cst_datasource")
	public DriverManagerDataSource getDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/springormdemos");
		ds.setUsername("root");
		ds.setPassword("root");
		return ds;
	}

	public HibernateJpaVendorAdapter getVendorAdaptor() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setGenerateDdl(false);
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
		return adapter;
	}

	@Bean(name = "cst_entityManagerFactory") // can have any name
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(DriverManagerDataSource dataSource) {
		LocalContainerEntityManagerFactoryBean factoryBuilder = new LocalContainerEntityManagerFactoryBean();
		factoryBuilder.setDataSource(dataSource);
		factoryBuilder.setJpaVendorAdapter(getVendorAdaptor());
		factoryBuilder.setPackagesToScan("com.ibc.training.entity");
		return factoryBuilder;
	}

	@Bean(name = "txManager")
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
	}

}
