package pl.pjwstk.jaz.allezon.webapp.authorization.session;

import pl.pjwstk.jaz.allezon.webapp.utilities.AllezonUtils;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class CurrentSession implements Serializable {

    @Inject
    AllezonUtils utils;

    private String name;
    private String surname;
    private String username;
    private boolean logged;
    private boolean admin;

    public void closeSession() {
        this.logged = false;
        utils.redirectToPage("/welcome.xhtml");
    }

    //Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
