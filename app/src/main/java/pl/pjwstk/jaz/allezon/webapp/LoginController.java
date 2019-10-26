package pl.pjwstk.jaz.allezon.webapp;

import pl.pjwstk.jaz.allezon.CurrentSession;
import pl.pjwstk.jaz.allezon.auth.ProfileEntity;
import pl.pjwstk.jaz.allezon.auth.ProfileRepository;
import pl.pjwstk.jaz.allezon.login.LoginRequest;
import pl.pjwstk.jaz.allezon.users.User;
import pl.pjwstk.jaz.allezon.users.UsersDatabase;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@RequestScoped
public class LoginController {
    @Inject
    private LoginRequest loginRequest;
    @Inject
    private CurrentSession currentSession;
    @Inject
    private UsersDatabase localDatabase;
    @Inject
    private ProfileRepository database;

    private boolean loginUnsuccessful = false;

    public void login() {
        System.out.println("Tried to log in using " + loginRequest.toString());

        String username = loginRequest.getUsername();

        if (database.checkIfUserExists(username)) {
            loginFromDatabase();
        }
        else if (localDatabase.checkIfUserExists(username)) {
            loginFromLocalDatabase();
        }
        else
            loginUnsuccessful = true;

        System.out.println("Logged: " + currentSession.isLogged());
    }

    private void loginFromDatabase() {
        ProfileEntity loggingUser = database.getUser(loginRequest.getUsername());
        if(loggingUser != null) {
            if (loggingUser.getPassword().equals(loginRequest.getPassword())) {
                currentSession.setName(loggingUser.getName());
                currentSession.setSurname(loggingUser.getSurname());
                currentSession.setLogged(true);
                redirectToIndex();
            }
            else
                loginUnsuccessful = true;

        }
    }

    private void loginFromLocalDatabase() {
            User loggingUser = localDatabase.getUser(loginRequest.getUsername());
            if (loggingUser.getPassword().equals(loginRequest.getPassword())) {
                currentSession.setName(loggingUser.getName());
                currentSession.setSurname(loggingUser.getSurname());
                currentSession.setLogged(true);
                redirectToIndex();
            }
            else
                loginUnsuccessful = true;
    }

    private void redirectToIndex() {
        //https://stackoverflow.com/questions/16776981/response-object-in-jsf
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getApplicationContextPath() + "/index.xhtml");
        } catch (IOException exception0) {
            System.out.println("Failed to redirect to index.xhtml");
        }
    }

    public boolean isLoginUnsuccessful() {
        return loginUnsuccessful;
    }

}