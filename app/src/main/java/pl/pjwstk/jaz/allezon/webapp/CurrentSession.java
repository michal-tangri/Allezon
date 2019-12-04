package pl.pjwstk.jaz.allezon.webapp;

import pl.pjwstk.jaz.allezon.webapp.AllezonUtils;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class CurrentSession implements Serializable {

    private String name;
    private String surname;
    private String username;
    private boolean isLogged;
    private boolean isAdmin;

    public void closeSession() {
        this.isLogged = false;
        AllezonUtils.redirectToPage("/welcome.xhtml");
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

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
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

}
