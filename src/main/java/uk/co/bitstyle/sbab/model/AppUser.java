package uk.co.bitstyle.sbab.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.LinkedList;

/**
 * A representation of a User of the system.
 * 
 * @author cspiking
 */
public class AppUser extends User {

    private final String email;

    private final String firstName;
    private final String lastName;

    private final long registrationTimeUtcMillis;

    private final boolean receiveUpdateEmails;

    private AppUser(String username, String password, boolean enabled, boolean accountNonExpired,
                    boolean credentialsNonExpired, boolean accountNonLocked,
                    Collection<? extends GrantedAuthority> authorities,
                    String firstName, String lastName, String email, long registrationTimeUtcMillis, boolean receiveUpdateEmails) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationTimeUtcMillis = registrationTimeUtcMillis;
        this.receiveUpdateEmails = receiveUpdateEmails;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getRegistrationTimeUtcMillis() {
        return registrationTimeUtcMillis;
    }

    public boolean receivesUpdateEmails() {
        return receiveUpdateEmails;
    }

    public boolean isPasswordSet() {
        return super.getPassword() == null || super.getPassword().length() == 0;
    }

    public AppUser copy() {
        return builderOf().build();
    }

    public AppUser.Builder builderOf() {
        Builder builder = new Builder();
        builder = builder.setUsername(this.getUsername());
        builder = builder.setAccountNonExpired(this.isAccountNonExpired());
        builder = builder.setAccountNonLocked(this.isAccountNonExpired());
        builder = builder.setAuthorities(new LinkedList<>(this.getAuthorities()));
        builder = builder.setCredentialsNonExpired(this.isCredentialsNonExpired());
        builder = builder.setEnabled(this.isEnabled());
        builder = builder.setFirstName(this.getFirstName());
        builder = builder.setLastName(this.getLastName());
        builder = builder.setEmail(this.getEmail());
        builder = builder.setPassword(this.getPassword());
        builder = builder.setReceiveUpdateEmails(this.receivesUpdateEmails());
        builder = builder.setRegistrationTime(this.getRegistrationTimeUtcMillis());
        return builder;
    }

    public static class Builder {
        private String username;
        private String password;

        private boolean enabled;
        private boolean accountNonExpired;
        private boolean credentialsNonExpired;
        private boolean accountNonLocked;
        private Collection<? extends GrantedAuthority> authorities;
        private String firstName;
        private String lastName;
        private String email;
        private long registrationTime;
        private boolean receiveUpdateEmails;

        public AppUser build() {
            return new AppUser(username,
                               password,
                               enabled,
                               accountNonExpired,
                               credentialsNonExpired,
                               accountNonLocked,
                               authorities,
                               firstName,
                               lastName,
                               email,
                               registrationTime,
                               receiveUpdateEmails);
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder setAccountNonExpired(boolean accountNonExpired) {
            this.accountNonExpired = accountNonExpired;
            return this;
        }

        public Builder setCredentialsNonExpired(boolean credentialsNonExpired) {
            this.credentialsNonExpired = credentialsNonExpired;
            return this;
        }

        public Builder setAccountNonLocked(boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
            return this;
        }

        public Builder setAuthorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setRegistrationTime(long registrationTime) {
            this.registrationTime = registrationTime;
            return this;
        }

        public Builder setReceiveUpdateEmails(boolean receiveUpdateEmails) {
            this.receiveUpdateEmails = receiveUpdateEmails;
            return this;
        }
    }

    /**
     * On the basis that there can only be one user with a given username - it's the id - equality is based on that.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        AppUser that = (AppUser) o;

        if (!getUsername().equals(that.getUsername())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AppUser{" +
               "email='" +
               email +
               '\'' +
               ", firstName='" +
               firstName +
               '\'' +
               ", lastName='" +
               lastName +
               '\'' +
               ", registrationTimeUtcMillis=" +
               registrationTimeUtcMillis +
               ", receiveUpdateEmails=" +
               receiveUpdateEmails +
               "} " +
               super.toString();
    }
}
