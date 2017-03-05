package uk.co.bitstyle.sbab.services.dao.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import uk.co.bitstyle.sbab.model.AppUser;

import java.util.Collection;

/**
 * Dao for User Objects within the system.
 *
 * @author cspiking
 */
public interface AppUserDao extends UserDetailsService {

    AppUserDaoOpResult<AppUser> createUser(AppUser userDetails);

    Collection<AppUser> getAllUsers();

    AppUser getUserByUsername(String username);

    AppUserDaoOpResult<AppUser> updateUser(AppUser userDetails);

    AppUserDaoOpResult<AppUser> deleteUserByUsername(String username);

    boolean userWithUsernameExists(String username);

}
