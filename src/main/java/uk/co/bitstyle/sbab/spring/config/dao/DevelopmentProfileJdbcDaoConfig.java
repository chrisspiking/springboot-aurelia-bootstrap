package uk.co.bitstyle.sbab.spring.config.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.bitstyle.sbab.spring.config.dao.silliness.DevSillyDao;
import uk.co.bitstyle.sbab.spring.config.dao.silliness.DummySillyDao;
import uk.co.bitstyle.sbab.spring.config.dao.silliness.SillyDao;

/**
 * Spring class configuring all JDBC Data Access.
 *
 * @author cspiking
 */
@Configuration
@Profile("development")
public class DevelopmentProfileJdbcDaoConfig extends BaseJdbcDaoConfig {

    @Bean
    public SillyDao getSillyDao() {
        if("false".equals(environment.getProperty("application.useDatabase"))) {
            return new DummySillyDao();
        } else {
            return new DevSillyDao();
        }
    }

}
