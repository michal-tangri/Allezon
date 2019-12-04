package pl.pjwstk.jaz.allezon.login;

import pl.pjwstk.jaz.allezon.webapp.AllezonUtils;
import pl.pjwstk.jaz.allezon.webapp.CurrentSession;
import pl.pjwstk.jaz.allezon.entities.ProfileEntity;
import pl.pjwstk.jaz.allezon.repositories.ProfileRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.mindrot.jbcrypt.BCrypt;

@Named
@RequestScoped
public class LoginController {

    @Inject
    private LoginRequest loginRequest;
    @Inject
    private CurrentSession currentSession;
    @Inject
    private ProfileRepository database;

    private boolean loginUnsuccessful = false;

    public void login() {
        System.out.println("Tried to log in using " + loginRequest.toString());

        if (database.checkIfUserExists(loginRequest.getUsername()))
            loginFromDatabase();
        else
            loginUnsuccessful = true;
    }

    private void loginFromDatabase() {
        ProfileEntity loggingUser = database.getUser(loginRequest.getUsername());

        if(loggingUser != null) {
            if (BCrypt.checkpw(loginRequest.getPassword(), loggingUser.getPassword())) {
                currentSession.setName(loggingUser.getName());
                currentSession.setSurname(loggingUser.getSurname());
                currentSession.setUsername(loggingUser.getUsername());
                currentSession.setLogged(true);
                currentSession.setAdmin(loggingUser.isAdmin());

                AllezonUtils.redirectToPage("/index.xhtml");
            }
            else
                loginUnsuccessful = true;
        }
    }

    public boolean isLoginUnsuccessful() {
        return loginUnsuccessful;
    }

}