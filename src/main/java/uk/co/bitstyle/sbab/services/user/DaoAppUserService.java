package uk.co.bitstyle.sbab.services.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import uk.co.bitstyle.sbab.model.AppUser;
import uk.co.bitstyle.sbab.services.dao.user.AppUserDao;

/**
 * @author cspiking
 */
public class DaoAppUserService implements AppUserService {

    private static final Logger logger = LoggerFactory.getLogger(DaoAppUserService.class);

    private final AppUserDao appUserDao;
    private final PasswordEncoder passwordEncoder;

    public DaoAppUserService(AppUserDao appUserDao, PasswordEncoder passwordEncoder) {
        this.appUserDao = appUserDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser getAppUserByUserName(String username) {
        return getUser(username, true);
    }

    @Override
    public void changePassword(String username, String plainTextPassword) {
        final AppUser user = getAppUserByUserName(username);
        if(user != null) {
            final AppUser appUser =
                    user.builderOf().setPassword(passwordEncoder.encode(plainTextPassword)).build();
            appUserDao.updateUser(appUser);
        } else {
            logger.warn("Attempt to change password for username {} but user does not exist.", username);
        }
    }

    @Override
    public boolean isPasswordValid(String username, String password) {
        final AppUser user = getUser(username, false);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    private AppUser getUser(String username, boolean returnUserWithEmptyCredentials) {
        AppUser userDetails = appUserDao.getUserByUsername(username);
        if (userDetails != null && returnUserWithEmptyCredentials) {
            userDetails.eraseCredentials();
        }
        return userDetails;
    }
}
