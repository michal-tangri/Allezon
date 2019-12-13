package pl.pjwstk.jaz.allezon.webapp.authorization.login;

import pl.pjwstk.jaz.allezon.webapp.utilities.AllezonUtils;
import pl.pjwstk.jaz.allezon.webapp.authorization.session.CurrentSession;
import pl.pjwstk.jaz.allezon.webapp.authorization.entities.ProfileEntity;
import pl.pjwstk.jaz.allezon.webapp.authorization.repositories.ProfileRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.mindrot.jbcrypt.BCrypt;

@Named
@RequestScoped
public class LoginController {

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private CurrentSession currentSession;

    @Inject
    private AllezonUtils utils;

    @Inject
    private LoginRequest loginRequest;

    private boolean loginUnsuccessful = false;

    public void login() {
        System.out.println("Tried to log in using " + loginRequest.toString());

        if (profileRepository.findUserByUsername(loginRequest.getUsername()) != null) {
            ProfileEntity loggingUser = profileRepository.findUserByUsername(loginRequest.getUsername());

            if(loggingUser != null) {
                if (BCrypt.checkpw(loginRequest.getPassword(), loggingUser.getPassword())) {
                    currentSession.setName(loggingUser.getName());
                    currentSession.setSurname(loggingUser.getSurname());
                    currentSession.setUsername(loggingUser.getUsername());
                    currentSession.setAdmin(loggingUser.isAdmin());
                    currentSession.setLogged(true);

                    utils.redirectToPage("/index.xhtml");
                }
                else
                    loginUnsuccessful = true;
            }
        }
        else
            loginUnsuccessful = true;
    }

    public boolean isLoginUnsuccessful() {
        return loginUnsuccessful;
    }

}