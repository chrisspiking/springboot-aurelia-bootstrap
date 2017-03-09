package uk.co.bitstyle.sbab.services.dao.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import paillard.florent.springframework.simplejdbcupdate.SimpleJdbcUpdate;
import uk.co.bitstyle.sbab.model.AppUser;
import uk.co.bitstyle.sbab.model.AppUser.Builder;
import uk.co.bitstyle.sbab.util.IdProvider;
import uk.co.bitstyle.sbab.util.IdProvider.UUIDProvider;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * AppUserDao for Postgres DB.
 *
 * @author cspiking
 */
public class PostgresAppUserDao implements AppUserDao {

    private static final Logger logger = LoggerFactory.getLogger(PostgresAppUserDao.class);

    private static final String USERS_TABLE_NAME = "app_user";

    private static final String USERS_FIELD_ID = "id";
    private static final String USERS_FIELD_USERNAME = "username";
    private static final String USERS_FIELD_PASSWORD = "password";
    private static final String USERS_FIELD_AUTHORITIES = "authorities";
    private static final String USERS_FIELD_ENABLED = "enabled";
    private static final String USERS_FIELD_ACCOUNT_NON_EXPIRED = "account_non_expired";
    private static final String USERS_FIELD_CREDENTIALS_NON_EXPIRED = "credentials_non_expired";
    private static final String USERS_FIELD_ACCOUNT_NON_LOCKED = "account_non_locked";
    private static final String USERS_FIELD_FIRST_NAME = "first_name";
    private static final String USERS_FIELD_LAST_NAME = "last_name";
    private static final String USERS_FIELD_EMAIL = "email";
    private static final String USERS_FIELD_REGISTRATION_TIME = "registration_time";
    private static final String USERS_FIELD_RECEIVE_EMAIL_UPDATES = "receive_email_updates";

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert appUserInserter;
    private final SimpleJdbcUpdate appUserUpdate;

    private final AppUserRowMapper appUserRowMapper;

    private final Object lockObject = new Object();

    private IdProvider idProvider = new UUIDProvider();

    public PostgresAppUserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        this.appUserInserter =
                new SimpleJdbcInsert(dataSource)
                        .withTableName(USERS_TABLE_NAME);

        this.appUserUpdate =
                new SimpleJdbcUpdate(dataSource)
                        .withTableName(USERS_TABLE_NAME)
                        .restrictingColumns(USERS_FIELD_USERNAME);

        this.appUserRowMapper = new AppUserRowMapper();
    }

    public void setIdProvider(IdProvider idProvider) {
        this.idProvider = idProvider;
    }

    @Override
    public Collection<AppUser> getAllUsers() {
        return jdbcTemplate.query("SELECT * FROM " + USERS_TABLE_NAME, appUserRowMapper);
    }

    @Override
    public AppUser getUserByUsername(String username) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + USERS_FIELD_USERNAME + " = ?",
                                               appUserRowMapper,
                                               username);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final AppUser userByUsername = getUserByUsername(username);
        if(userByUsername == null) {
            throw new UsernameNotFoundException("Username '" + username + "' not found.");
        }
        return userByUsername;
    }

    @Override
    public AppUserDaoOpResult<AppUser> createUser(AppUser userDetails) {
        synchronized (lockObject) {
            final boolean userExists = userWithUsernameExists(userDetails.getUsername());
            if(!userExists) {

                final String userId = idProvider.provideId();
                final Map<String, Object> parameters = getAppUserStringObjectMap(userDetails);
                parameters.put(USERS_FIELD_ID, userId);

                appUserInserter.execute(parameters);
                logger.info("Created new user with id {} and username {}", userId, userDetails.getUsername());

                return AppUserDaoOpResult.Builder
                        .<AppUser>anAppUserDaoOpResult()
                        .withSuccess(true)
                        .withResultObject(userDetails)
                        .build();
            } else {
                return AppUserDaoOpResult.Builder
                        .<AppUser>anAppUserDaoOpResult()
                        .withSuccess(false)
                        .withMessage("User with username " + userDetails + " already exists.")
                        .build();
            }
        }
    }

    @Override
    public AppUserDaoOpResult<AppUser> updateUser(AppUser userDetails) {
        synchronized (lockObject) {
            final boolean userExists = userWithUsernameExists(userDetails.getUsername());
            if(userExists) {
                final Map<String, Object> parameters = getAppUserStringObjectMap(userDetails);

                final Map<String, Object> keyParams = new HashMap<>();
                keyParams.put(USERS_FIELD_USERNAME, userDetails.getUsername());

                appUserUpdate.execute(parameters, keyParams);
                logger.info("Updated user with username {}", userDetails.getUsername());

                return AppUserDaoOpResult.Builder
                        .<AppUser>anAppUserDaoOpResult()
                        .withSuccess(true)
                        .withResultObject(userDetails)
                        .build();
            } else {
                return AppUserDaoOpResult.Builder
                        .<AppUser>anAppUserDaoOpResult()
                        .withSuccess(false)
                        .withMessage("User with username " + userDetails + " does not exist.")
                        .build();
            }
        }
    }

    private Map<String, Object> getAppUserStringObjectMap(AppUser userDetails) {
        final String authoritiesStr =
                userDetails.getAuthorities().stream()
                           .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        final Map<String, Object> parameters = new HashMap<>(12);
        parameters.put(USERS_FIELD_USERNAME, userDetails.getUsername());
        parameters.put(USERS_FIELD_PASSWORD, userDetails.getPassword());
        parameters.put(USERS_FIELD_AUTHORITIES, authoritiesStr);
        parameters.put(USERS_FIELD_ENABLED, userDetails.isEnabled());
        parameters.put(USERS_FIELD_ACCOUNT_NON_EXPIRED, userDetails.isAccountNonExpired());
        parameters.put(USERS_FIELD_CREDENTIALS_NON_EXPIRED, userDetails.isCredentialsNonExpired());
        parameters.put(USERS_FIELD_ACCOUNT_NON_LOCKED, userDetails.isAccountNonLocked());
        parameters.put(USERS_FIELD_FIRST_NAME, userDetails.getFirstName());
        parameters.put(USERS_FIELD_LAST_NAME, userDetails.getLastName());
        parameters.put(USERS_FIELD_EMAIL, userDetails.getEmail());
        parameters.put(USERS_FIELD_REGISTRATION_TIME, userDetails.getRegistrationTimeUtcMillis());
        parameters.put(USERS_FIELD_RECEIVE_EMAIL_UPDATES, userDetails.receivesUpdateEmails());
        return parameters;
    }

    @Override
    public AppUserDaoOpResult<AppUser> deleteUserByUsername(String username) {
        synchronized (lockObject) {
            final AppUser appUser = getUserByUsername(username);
            if(appUser != null) {
                jdbcTemplate.update("DELETE FROM " + USERS_TABLE_NAME + " WHERE username = ?", username);
                return AppUserDaoOpResult.Builder
                        .<AppUser>anAppUserDaoOpResult()
                        .withSuccess(true)
                        .withResultObject(appUser)
                        .build();
            } else {
                return AppUserDaoOpResult.Builder
                        .<AppUser>anAppUserDaoOpResult()
                        .withSuccess(false)
                        .withMessage("User with username " + username + " does not exists.")
                        .build();
            }
        }
    }

    @Override
    public boolean userWithUsernameExists(String username) {
        return getUserByUsername(username) != null;
    }

    private static final class AppUserRowMapper implements RowMapper<AppUser> {

        @Override
        public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {

            final String[] authoritiesNamesList = rs.getString(USERS_FIELD_AUTHORITIES).split(",");
            final List<GrantedAuthority> authorities = new ArrayList<>();
            for(Object el : authoritiesNamesList) {
                authorities.add(new SimpleGrantedAuthority((String) el));
            }

            final AppUser.Builder builder = new Builder();
            return builder.setAccountNonExpired(rs.getBoolean(USERS_FIELD_ACCOUNT_NON_EXPIRED))
                          .setAccountNonLocked(rs.getBoolean(USERS_FIELD_ACCOUNT_NON_LOCKED))
                          .setAuthorities(authorities)
                          .setCredentialsNonExpired(rs.getBoolean(USERS_FIELD_CREDENTIALS_NON_EXPIRED))
                          .setEmail(rs.getString(USERS_FIELD_EMAIL))
                          .setEnabled(rs.getBoolean(USERS_FIELD_ENABLED))
                          .setFirstName(rs.getString(USERS_FIELD_FIRST_NAME))
                          .setLastName(rs.getString(USERS_FIELD_LAST_NAME))
                          .setPassword(rs.getString(USERS_FIELD_PASSWORD))
                          .setReceiveUpdateEmails(rs.getBoolean(USERS_FIELD_RECEIVE_EMAIL_UPDATES))
                          .setRegistrationTime(rs.getLong(USERS_FIELD_REGISTRATION_TIME))
                          .setUsername(rs.getString(USERS_FIELD_USERNAME))
                          .build();

        }
    }
}
