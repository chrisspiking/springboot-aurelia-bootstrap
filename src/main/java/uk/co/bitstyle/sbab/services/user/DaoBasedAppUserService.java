package uk.co.bitstyle.sbab.services.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import uk.co.bitstyle.sbab.model.AppUser;
import uk.co.bitstyle.sbab.services.dao.user.AppUserDao;
import uk.co.bitstyle.sbab.services.dao.user.AppUserDaoOpResult;
import uk.co.bitstyle.sbab.services.dao.user.AppUserRoles;

import java.util.LinkedList;
import java.util.List;

/**
 * @author cspiking
 */
public class DaoBasedAppUserService implements AppUserService {

    private static final Logger logger = LoggerFactory.getLogger(DaoBasedAppUserService.class);

    private final AppUserDao appUserDao;
    private final PasswordEncoder passwordEncoder;

    public DaoBasedAppUserService(AppUserDao appUserDao, PasswordEncoder passwordEncoder) {
        this.appUserDao = appUserDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser getAppUserByUserName(String username) {
        return getUser(username, true);
    }

    @Override
    public AppUser registerNewUser(AppUserRegistrationRequest appUserRegistrationRequest) {
        final AppUserRegistrationRequest request = appUserRegistrationRequest.copy();
        final AppUser newAppUser = createAppUserFromRegistrationRequest(request);
        final AppUserDaoOpResult<AppUser> response = appUserDao.createUser(newAppUser);
        if(response.isSuccess()) {
            return response.getResultObject();
        } else {
            throw new UserServiceException(response.getMessage(), response.getException());
        }
    }

    private AppUser createAppUserFromRegistrationRequest(AppUserRegistrationRequest request) {
        final List<GrantedAuthority> authorityList = new LinkedList<>();
        authorityList.add(new SimpleGrantedAuthority(AppUserRoles.ROLE_LOGIN.name()));

        // Should we check for existing user here when we know the Dao does it (should we assume that?)
        return new AppUser.Builder()
                        .setAccountNonExpired(true)
                        .setAccountNonLocked(true)
                        .setAuthorities(authorityList)
                        .setCredentialsNonExpired(true)
                        .setEmail(request.getEmail())
                        .setEnabled(true)
                        .setFirstName(request.getFirstName())
                        .setLastName(request.getLastName())
                        .setPassword(passwordEncoder.encode(request.getPassword()))
                        .setUsername(request.getUsername())
                        .setReceiveUpdateEmails(true)
                        .setRegistrationTime(System.currentTimeMillis())
                        .build();
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
