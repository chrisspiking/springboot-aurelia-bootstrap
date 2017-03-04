package uk.co.bitstyle.sbab.spring.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cspiking
 */
public class DummySimpleDao implements SimpleDao {

    private static final Logger logger = LoggerFactory.getLogger(DummySimpleDao.class);

    @Override
    public void doSomething() {
        logger.info("Dummy SimpleDAO Here!");
    }
}
