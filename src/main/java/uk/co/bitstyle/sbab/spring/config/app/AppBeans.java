package uk.co.bitstyle.sbab.spring.config.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import uk.co.bitstyle.sbab.services.dao.user.AppUserDao;
import uk.co.bitstyle.sbab.services.user.AppUserService;
import uk.co.bitstyle.sbab.services.user.DaoAppUserService;
import uk.co.bitstyle.sbab.spring.config.dao.BaseJdbcDaoConfig;

/**
 * App config
 *
 * @author cspiking
 */
@Configuration
@Import(BaseJdbcDaoConfig.class)
public class AppBeans {

    @Autowired
    private AppUserDao appUserDao;

    @Bean
    public AppUserService appUserService() {
        return new DaoAppUserService(appUserDao, null);
    }

}
