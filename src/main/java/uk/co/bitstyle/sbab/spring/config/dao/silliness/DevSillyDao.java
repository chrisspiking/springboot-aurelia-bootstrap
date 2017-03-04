package uk.co.bitstyle.sbab.spring.config.dao.silliness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * To test some Spring stuff
 *
 * @author cspiking
 */
public class DevSillyDao implements SillyDao {

    private static final Logger logger = LoggerFactory.getLogger(DevSillyDao.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void doSomething() {
        logger.info("I PROD have been called : " + jdbcTemplate.getDataSource().toString());
    }

}
