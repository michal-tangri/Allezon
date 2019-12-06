package pl.pjwstk.jaz.allezon.login;

import pl.pjwstk.jaz.allezon.webapp.AllezonUtils;
import pl.pjwstk.jaz.allezon.webapp.CurrentSession;
import pl.pjwstk.jaz.allezon.auth.ProfileEntity;
import pl.pjwstk.jaz.allezon.auth.ProfileRepository;

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
    private ProfileRepository profileRepository;

    @Inject
    private CurrentSession currentSession;

    @Inject
    private AllezonUtils utils;

    private boolean loginUnsuccessful = false;

    public void login() {
        System.out.println("Tried to log in using " + loginRequest.toString());

        if (profileRepository.findUser(loginRequest.getUsername()) != null) {
            ProfileEntity loggingUser = profileRepository.findUser(loginRequest.getUsername());

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