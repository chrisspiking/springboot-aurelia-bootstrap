package uk.co.bitstyle.sbab.spring.dao;

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
public class DevSimpleDao implements SimpleDao {

    private static final Logger logger = LoggerFactory.getLogger(DevSimpleDao.class);

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
