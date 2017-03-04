package uk.co.bitstyle.sbab.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import uk.co.bitstyle.sbab.spring.dao.DummySimpleDao;
import uk.co.bitstyle.sbab.spring.dao.ProdSimpleDao;
import uk.co.bitstyle.sbab.spring.dao.SimpleDao;

/**
 * Spring class configuring all JDBC Data Access.
 *
 * @author cspiking
 */
@Configuration
@Profile("production")
public class ProductionJdbcDaoConfig {

    @Autowired
    private Environment environment;

    @Bean
    public SimpleDao getSimpleDao() {
        if("false".equals(environment.getProperty("application.useDatabase"))) {
            return new DummySimpleDao();
        } else {
            return new ProdSimpleDao();
        }
    }

}
