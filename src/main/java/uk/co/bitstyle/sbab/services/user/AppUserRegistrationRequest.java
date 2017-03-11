package uk.co.bitstyle.sbab.services.user;

/**
 * Object to model request to register a user
 *
 * @author cspiking
 */
public class AppUserRegistrationRequest {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private boolean receiveEmails;

    public AppUserRegistrationRequest copy() {
        return new AppUserRegistrationRequest()
                .withUsername(username)
                .withPassword(password)
                .withEmail(email)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withReceiveEmails(receiveEmails);

    }
    public String getUsername() {
        return username;
    }

    public AppUserRegistrationRequest withUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AppUserRegistrationRequest withPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AppUserRegistrationRequest withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public AppUserRegistrationRequest withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AppUserRegistrationRequest withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public boolean isReceiveEmails() {
        return receiveEmails;
    }

    public AppUserRegistrationRequest withReceiveEmails(boolean receiveEmails) {
        this.receiveEmails = receiveEmails;
        return this;
    }


}
