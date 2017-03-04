package uk.co.bitstyle.sbab.services.dao;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import uk.co.bitstyle.sbab.model.AppUser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author chrisspiking
 */
public class InMemoryAppUserDao implements AppUserDao {

    private Map<String, AppUser> appUserDetailsMap = new HashMap<>();

    private final PasswordEncoder passwordEncoder;

    public InMemoryAppUserDao(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser getUserByUsername(String username) {
        return appUserDetailsMap.get(username);
    }

    @Override
    public AppUserDaoOpResult<AppUser> deleteUserByUsername(String username) {
        final AppUser removedUser = appUserDetailsMap.remove(username);
        if(removedUser != null) {
            return AppUserDaoOpResult.Builder.<AppUser>anAppUserDaoOpResult()
                    .withSuccess(true).withResultObject(removedUser).build();
        } else {
            return AppUserDaoOpResult.Builder.<AppUser>anAppUserDaoOpResult()
                    .withSuccess(false).withMessage("Username " + username + " not found.").build();
        }
    }

    @Override
    public AppUserDaoOpResult<AppUser> createUser(AppUser appUser) {
        if(appUserDetailsMap.containsKey(appUser.getUsername())) {
            return AppUserDaoOpResult.Builder
                    .<AppUser>anAppUserDaoOpResult()
                    .withSuccess(false)
                    .withMessage("Username " + appUser.getUsername() + " already exists.").build();
        }
        final AppUser copyOfAppUserDetails =
                constructAppUser(appUser.getUsername(),
                                 passwordEncoder.encode(appUser.getPassword()),
                                 appUser.getFirstName(),
                                 appUser.getLastName(),
                                 appUser.getEmail(),
                                 appUser.getRegistrationTimeUtcMillis(),
                                 appUser.receivesUpdateEmails(),
                                 appUser.isAccountNonExpired(),
                                 appUser.isAccountNonLocked(),
                                 appUser.isCredentialsNonExpired(),
                                 appUser.isEnabled(),
                                 new HashSet<>(appUser.getAuthorities()));
        appUserDetailsMap.put(appUser.getEmail(), copyOfAppUserDetails);
        return AppUserDaoOpResult.Builder.<AppUser>anAppUserDaoOpResult().withSuccess(true).withResultObject(copyOfAppUserDetails).build();
    }

    @Override
    public AppUserDaoOpResult<AppUser> updateUser(AppUser appUser) {
        if(!appUserDetailsMap.containsKey(appUser.getUsername())) {
            return AppUserDaoOpResult.Builder
                    .<AppUser>anAppUserDaoOpResult()
                    .withSuccess(false)
                    .withMessage("Username " + appUser.getUsername() + " does not exist.").build();
        }
        AppUser copyOfAppUserDetails =
                constructAppUser(appUser.getUsername(),
                                 passwordEncoder.encode(appUser.getPassword()),
                                 appUser.getFirstName(),
                                 appUser.getLastName(),
                                 appUser.getEmail(),
                                 appUser.getRegistrationTimeUtcMillis(),
                                 appUser.receivesUpdateEmails(),
                                 appUser.isAccountNonExpired(),
                                 appUser.isAccountNonLocked(),
                                 appUser.isCredentialsNonExpired(),
                                 appUser.isEnabled(),
                                 new HashSet<>(appUser.getAuthorities()));
        appUserDetailsMap.put(appUser.getEmail(), copyOfAppUserDetails);
        return AppUserDaoOpResult.Builder.<AppUser>anAppUserDaoOpResult().withSuccess(true).withResultObject(copyOfAppUserDetails).build();
    }

    @Override
    public boolean userWithUsernameExists(String username) {
        return appUserDetailsMap.get(username) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = appUserDetailsMap.get(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException(username);
        }
        return userDetails;
    }

    private AppUser constructAppUser(String username, String password,
                                     String firstName, String lastName,
                                     String email,
                                     long registrationTime, boolean receiveUpdateEmails,
                                     boolean accountNonExpired, boolean accountNonLocked,
                                     boolean credentialsNonExpired, boolean enabled,
                                     Set<GrantedAuthority> authorities) {
        AppUser.Builder builder = new AppUser.Builder();

        builder = builder.setUsername(username);
        builder = builder.setAccountNonExpired(accountNonExpired);
        builder = builder.setAccountNonLocked(accountNonLocked);
        builder = builder.setAuthorities(authorities);
        builder = builder.setCredentialsNonExpired(credentialsNonExpired);
        builder = builder.setEnabled(enabled);
        builder = builder.setFirstName(firstName);
        builder = builder.setLastName(lastName);
        builder = builder.setEmail(email);
        builder = builder.setPassword(password);
        builder = builder.setReceiveUpdateEmails(receiveUpdateEmails);
        builder = builder.setRegistrationTime(registrationTime);

        return builder.build();
    }
}
