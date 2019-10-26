package pl.pjwstk.jaz.allezon.webapp;

import pl.pjwstk.jaz.allezon.CurrentSession;
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
    private UsersDatabase usersDatabase;

    private boolean loginUnsuccessful = false;

    public void login() {
        System.out.println("Tried to log in using " + loginRequest.toString());
        if (usersDatabase.checkIfUserExists(loginRequest.getUsername())) {
            User loggingUser = usersDatabase.getUser(loginRequest.getUsername());
            if (loggingUser.getPassword().equals(loginRequest.getPassword())) {
                currentSession.setName(loggingUser.getName());
                currentSession.setSurname(loggingUser.getSurname());
                currentSession.setLogged(true);

                loginUnsuccessful = false;

                //https://stackoverflow.com/questions/16776981/response-object-in-jsf
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                try {
                    externalContext.redirect(externalContext.getApplicationContextPath() + "/index.xhtml");
                } catch (IOException exception0) {
                    System.out.println("Failed to redirect to index.xhtml");
                }
            }
            else
                loginUnsuccessful = true;
        }
        else
            loginUnsuccessful = true;

        System.out.println("Logged: " + currentSession.isLogged());
    }

    public boolean isLoginUnsuccessful() {
        return loginUnsuccessful;
    }

}