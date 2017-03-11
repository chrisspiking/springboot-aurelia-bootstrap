package uk.co.bitstyle.sbab.services.dao.user;

/**
 * @author cspiking
 */
public enum AppUserRoles {

    ROLE_LOGIN("LOGIN");

    private final String baseName;

    private AppUserRoles(String baseName) {
        this.baseName = baseName;
    }

    public String getBaseName() {
        return baseName;
    }

}
