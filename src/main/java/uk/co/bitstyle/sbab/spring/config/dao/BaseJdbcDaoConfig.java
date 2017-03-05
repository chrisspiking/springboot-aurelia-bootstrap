package uk.co.bitstyle.sbab.spring.config.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import uk.co.bitstyle.sbab.services.dao.user.AppUserDao;
import uk.co.bitstyle.sbab.services.dao.user.InMemoryAppUserDao;

import javax.sql.DataSource;

/**
 * @author cspiking
 */
@Configuration
public class BaseJdbcDaoConfig {

    @Autowired
    protected Environment environment;

    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AppUserDao appUserDao() {
        return new InMemoryAppUserDao(passwordEncoder());
    }
}
