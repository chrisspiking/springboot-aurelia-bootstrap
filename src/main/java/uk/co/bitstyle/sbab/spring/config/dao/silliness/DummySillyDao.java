package uk.co.bitstyle.sbab.spring.config.dao.silliness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cspiking
 */
public class DummySillyDao implements SillyDao {

    private static final Logger logger = LoggerFactory.getLogger(DummySillyDao.class);

    @Override
    public void doSomething() {
        logger.info("Dummy SimpleDAO Here!");
    }
}
