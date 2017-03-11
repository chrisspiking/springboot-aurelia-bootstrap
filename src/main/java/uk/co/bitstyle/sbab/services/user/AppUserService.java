package uk.co.bitstyle.sbab.services.user;

import uk.co.bitstyle.sbab.model.AppUser;

/**
 * @author cspiking
 */
public interface AppUserService {

    AppUser getAppUserByUserName(String username);

    AppUser registerNewUser(AppUserRegistrationRequest appUserRegistrationRequest);

    void changePassword(String username, String plainTextPassword);

    boolean isPasswordValid(String username, String password);

}
