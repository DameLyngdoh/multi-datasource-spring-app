package io.damelyngdoh.multidatasourcespringapp.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Data source configuration which generates 
 * {@link io.damelyngdoh.multidatasourcespringapp.config.AlphaDataSourceConfiguration#getAlphaDataSource DataSource}, 
 * {@link io.damelyngdoh.multidatasourcespringapp.config.AlphaDataSourceConfiguration#getAlphaEntityManagerFactory EntityManagerFactory} and 
 * {@link io.damelyngdoh.multidatasourcespringapp.config.AlphaDataSourceConfiguration#getAlphaTransactionManager TransactionManager} 
 * beans which are required for performing persistence operations on {@link io.damelyngdoh.multidatasourcespringapp.domain.alpha Alpha domain} objects 
 * which also involves {@link io.damelyngdoh.multidatasourcespringapp.repository.alpha Alpha repository} proxy interface.
 * 
 * @author Dame Lyngdoh
 */
@Configuration
@EnableJpaRepositories(
    basePackages = { "io.damelyngdoh.multidatasourcespringapp.repository.alpha" },
    entityManagerFactoryRef = "alphaEntityManagerFactory",
    transactionManagerRef = "alphaTransactionManager"
)
@EnableTransactionManagement
public class AlphaDataSourceConfiguration {
    
    private final Logger log = LoggerFactory.getLogger(AlphaDataSourceConfiguration.class);
    
    /**
     * Instantiate DataSourceProperties with properties from properties file with specified prefix.
     * @return Returns auto-configured DataSourceProperties object with specified prefix.
     */
    @Primary
    @Bean(name = "alphaDataSourceProperties")
    @ConfigurationProperties(prefix = "persistence-config.alpha.datasource")
    public DataSourceProperties getAlphaDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Instantiates DataSource bean with specified properties.
     * @param dataSourceProperties Properties for the DataSource bean.
     * @return Returns DataSource object as bean.
     */
    @Primary
    @Bean(name = "alphaDataSource")
    public DataSource getAlphaDataSource(
        final @Qualifier("alphaDataSourceProperties") DataSourceProperties dataSourceProperties
    ) {
        HikariDataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        dataSource.setAutoCommit(false);
        return dataSource;
    }

    /**
     * Instantiate JpaProperties with properties from properties file with specified prefix.
     * @return Returns auto-configured JpaProperties object with specified prefix.
     */
    @Primary
    @Bean("alphaJpaProperties")
    @ConfigurationProperties(prefix = "persistence-config.alpha.jpa")
    public JpaProperties alphaJpaProperties() {
        return new JpaProperties();
    }

    /**
     * Instantiates entity manager factory bean for this data source configuration.
     * @param dataSource DataSource bean for the entity manager.
     * @param jpaProperties JpaProperties for the entity manager.
     * @return Returns entity manager factory bean.
     */
    @Primary
    @Bean("alphaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getAlphaEntityManagerFactory(
        EntityManagerFactoryBuilder builder,
        final @Qualifier("alphaDataSource") DataSource dataSource,
        final @Qualifier("alphaJpaProperties") JpaProperties jpaProperties
    ) {
        
        // Configuring Hibernate Jpa Vendor Adapter
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        jpaVendorAdapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
        jpaVendorAdapter.setGenerateDdl(jpaProperties.isGenerateDdl());
        jpaVendorAdapter.setShowSql(jpaProperties.isShowSql());
        
        // Configuring Entity Manager Factory
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setJpaPropertyMap(jpaProperties.getProperties());
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPackagesToScan("io.damelyngdoh.multidatasourcespringapp.domain.alpha");
        emf.setPersistenceUnitName(Constants.ALPHA_PERSISTENCE_UNIT);
        emf.afterPropertiesSet();
        
        log.debug("EMF Complete.");
        return emf;
    }

    /**
     * Instantiates transaction manager for this data source configuration.
     * @param entityManagerFactory Entity manager factory bean of this data source configuration.
     * @param jpaProperties JPA Properties of JPA of this data source.
     * @return Returns configured transaction manager.
     */
    @Primary
    @Bean(name = "alphaTransactionManager")
    public JpaTransactionManager getAlphaTransactionManager(
        final @Qualifier("alphaEntityManagerFactory") EntityManagerFactory entityManagerFactory,
        final @Qualifier("alphaJpaProperties") JpaProperties jpaProperties
    ) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(entityManagerFactory);
        jpaTransactionManager.setPersistenceUnitName(Constants.ALPHA_PERSISTENCE_UNIT);
        jpaTransactionManager.setJpaPropertyMap(jpaProperties.getProperties());
        return jpaTransactionManager;
    }
}
