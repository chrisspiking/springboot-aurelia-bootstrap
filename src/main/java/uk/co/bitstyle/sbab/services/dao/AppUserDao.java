package uk.co.bitstyle.sbab.services.dao;

import org.springframework.security.core.userdetails.UserDetailsService;
import uk.co.bitstyle.sbab.model.AppUser;

/**
 * Dao for User Objects within the system.
 *
 * @author cspiking
 */
public interface AppUserDao extends UserDetailsService {

    AppUserDaoOpResult<AppUser> createUser(AppUser userDetails);

    AppUser getUserByUsername(String username);

    AppUserDaoOpResult<AppUser> updateUser(AppUser userDetails);

    AppUserDaoOpResult<AppUser> deleteUserByUsername(String username);

    boolean userWithUsernameExists(String username);

}
