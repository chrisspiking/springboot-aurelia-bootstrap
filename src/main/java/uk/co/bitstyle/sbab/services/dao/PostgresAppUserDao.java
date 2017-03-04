package uk.co.bitstyle.sbab.services.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uk.co.bitstyle.sbab.model.AppUser;

import javax.sql.DataSource;

/**
 * @author cspiking
 */
public class PostgresAppUserDao implements AppUserDao {

    private final JdbcTemplate jdbcTemplate;

    public PostgresAppUserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public AppUserDaoOpResult<AppUser> createUser(AppUser userDetails) {
        return null;
    }

    @Override
    public AppUser getUserByUsername(String username) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public AppUserDaoOpResult<AppUser> updateUser(AppUser userDetails) {
        return null;
    }

    @Override
    public AppUserDaoOpResult<AppUser> deleteUserByUsername(String username) {
        return null;
    }

    @Override
    public boolean userWithUsernameExists(String username) {
        return false;
    }
}
