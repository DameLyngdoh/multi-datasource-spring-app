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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Data source configuration which generates 
 * {@link io.damelyngdoh.multidatasourcespringapp.config.BetaDataSourceConfiguration#getBetaDataSource DataSource}, 
 * {@link io.damelyngdoh.multidatasourcespringapp.config.BetaDataSourceConfiguration#getBetaEntityManagerFactory EntityManagerFactory} and 
 * {@link io.damelyngdoh.multidatasourcespringapp.config.BetaDataSourceConfiguration#getBetaTransactionManager TransactionManager} 
 * beans which are required for performing persistence operations on {@link io.damelyngdoh.multidatasourcespringapp.domain.alpha Alpha domain} objects 
 * which also involves {@link io.damelyngdoh.multidatasourcespringapp.repository.alpha Alpha repository} proxy interface.
 * 
 * @author Dame Lyngdoh
 */
@Configuration
@EnableJpaRepositories(
    basePackages = { "io.damelyngdoh.multidatasourcespringapp.repository.beta" },
    entityManagerFactoryRef = "betaEntityManagerFactory",
    transactionManagerRef = "betaTransactionManager"
)
@EnableTransactionManagement
public class BetaDataSourceConfiguration {
    
    private final Logger log = LoggerFactory.getLogger(BetaDataSourceConfiguration.class);
    
    /**
     * Instantiate DataSourceProperties with properties from properties file with specified prefix.
     * @return Returns auto-configured DataSourceProperties object with specified prefix.
     */
    @Bean(name = "betaDataSourceProperties")
    @ConfigurationProperties(prefix = "persistence-config.beta.datasource")
    public DataSourceProperties getBetaDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Instantiates DataSource bean with specified properties.
     * @param dataSourceProperties Properties for the DataSource bean.
     * @return Returns DataSource object as bean.
     */
    @Bean(name = "betaDataSource")
    public DataSource getBetaDataSource(
        final @Qualifier("betaDataSourceProperties") DataSourceProperties dataSourceProperties
    ) {
        HikariDataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        dataSource.setAutoCommit(false);
        return dataSource;
    }

    /**
     * Instantiate JpaProperties with properties from properties file with specified prefix.
     * @return Returns auto-configured JpaProperties object with specified prefix.
     */
    @Bean("betaJpaProperties")
    @ConfigurationProperties(prefix = "persistence-config.beta.jpa")
    public JpaProperties getBetaJpaProperties() {
        return new JpaProperties();
    }

    /**
     * Instantiates entity manager factory bean for this data source configuration.
     * @param dataSource DataSource bean for the entity manager.
     * @param jpaProperties JpaProperties for the entity manager.
     * @return Returns entity manager factory bean.
     */
    @Bean("betaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getBetaEntityManagerFactory(
        final @Qualifier("betaDataSource") DataSource dataSource,
        final @Qualifier("betaJpaProperties") JpaProperties jpaProperties
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
        emf.setPackagesToScan("io.damelyngdoh.multidatasourcespringapp.domain.beta");
        emf.setPersistenceUnitName(Constants.BETA_PERSISTENCE_UNIT);
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
    @Bean(name = "betaTransactionManager")
    public JpaTransactionManager getBetaTransactionManager(
        final @Qualifier("betaEntityManagerFactory") EntityManagerFactory entityManagerFactory,
        final @Qualifier("betaJpaProperties") JpaProperties jpaProperties
    ) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(entityManagerFactory);
        jpaTransactionManager.setPersistenceUnitName(Constants.BETA_PERSISTENCE_UNIT);
        jpaTransactionManager.setJpaPropertyMap(jpaProperties.getProperties());
        return jpaTransactionManager;
    }
}
