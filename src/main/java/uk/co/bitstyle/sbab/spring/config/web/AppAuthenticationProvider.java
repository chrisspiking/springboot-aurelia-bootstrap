package uk.co.bitstyle.sbab.spring.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uk.co.bitstyle.sbab.model.AppUser;
import uk.co.bitstyle.sbab.services.user.AppUserService;

import java.util.LinkedList;
import java.util.List;

/**
 * @author cspiking
 */
public class AppAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AppUserService appUserService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String userName = authentication.getName();
        final AppUser appUser = appUserService.getAppUserByUserName(userName);
        if (appUser == null) {
            throw new UsernameNotFoundException(userName);
        }

        final String password = authentication.getCredentials().toString();
        if (appUserService.isPasswordValid(userName, password)) {
            final List<GrantedAuthority> grantedAuths = new LinkedList<>(appUser.getAuthorities());
            return new UsernamePasswordAuthenticationToken(userName, password, grantedAuths);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
