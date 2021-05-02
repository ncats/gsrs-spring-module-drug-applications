package gov.nih.ncats.application;

import gov.nih.ncats.application.repositories.*;
import gsrs.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.zaxxer.hikari.HikariDataSource;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

@Configuration
//@EnableTransactionManagement
//@PropertySource("classpath:application.properties")
//@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
 //       classes = ApplicationRepository.class))
/*
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManager",
        transactionManagerRef = "coreTransactionManager",
        basePackages = {"ix","gsrs", "gov.nih.ncats"}
     //   ,
     //   excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
    //            ApplicationRepository.class})  }
                )
*/

public class DataSourceConfigurationCore {

    @Autowired
    Environment environment;

    /*
    @Bean (name="dataSource")
   // @ConfigurationProperties(prefix = "spring.datasource")
    @Primary
    public DataSource dataSource() {
        System.out.println("*********** DATABASE USERNAME: ************ " + environment.getProperty("spring.datasource.username"));
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUrl("jdbc:oracle:thin:@//D15311532.fda.gov:1532/SRSIDDEV");
        dataSource.setUsername("GSRS_PROD");
        dataSource.setPassword("prd_JAN_2016");

     //   dataSource.setUsername("SRSCID");
      //  dataSource.setPassword("App4gsrs!");
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        return dataSource;


      //  final DriverManagerDataSource dataSource = new DriverManagerDataSource();
      //  dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
      //  dataSource.setUrl(environment.getProperty("spring.datasource.url"));
     //   dataSource.setUsername(environment.getProperty("spring.datasource.username"));
     //   dataSource.setPassword(environment.getProperty("spring.datasource.password"));
     //   return dataSource;

   // }
    @Bean (name = "entityManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(
                new String[] {"ix","gsrs", "gov.nih.ncats"});

        em.setPersistenceUnitName("entityCore");

        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.ddl-auto", "none");
        properties.put("show-sql", "true");
      //  properties.put("hibernate.dialect","org.hibernate.dialect.Oracle10gDialect");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean (name = "coreTransactionManager")
    @Primary
    public PlatformTransactionManager coreTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                entityManagerFactory().getObject());
        return transactionManager;
    }
   */

    @Bean
    public DataSource datasource() {

            System.out.println("****************** " + environment.getProperty("spring.datasource.username"));
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
            dataSource.setUrl("jdbc:oracle:thin:@//D15311532.fda.gov:1532/SRSIDDEV");
          //  dataSource.setUsername("SRSCID");
          //  dataSource.setPassword("App4gsrs!");
            dataSource.setUsername("GSRS_PROD");
            dataSource.setPassword("prd_JAN_2016");

            return dataSource;


            //final DriverManagerDataSource dataSource = new DriverManagerDataSource();
           // dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
           // dataSource.setUrl(environment.getProperty("spring.datasource.url"));
           // dataSource.setUsername(environment.getProperty("spring.datasource.username"));
          //  dataSource.setPassword(environment.getProperty("spring.datasource.password"));
          //  return dataSource;

    }

}
