package pl.pjwstk.jaz.allezon.webapp.authorization.register;

import pl.pjwstk.jaz.allezon.webapp.utilities.AllezonUtils;
import pl.pjwstk.jaz.allezon.webapp.authorization.repositories.ProfileRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class RegisterController {

    @Inject
    private RegisterRequest registerRequest;

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private AllezonUtils utils;

    private boolean passwordsDoNotMatch = false;
    private boolean usernameAlreadyInDatabase = false;

    public void register() {
        System.out.println("Tried to register using " + registerRequest.toString());

        if (profileRepository.findUserByUsername(registerRequest.getUsername()) == null)
            if(registerRequest.doPasswordsMatch()) {
                profileRepository.save(registerRequest.toProfileEntity());
                utils.redirectToPage("/login.xhtml");
            } else
                passwordsDoNotMatch = true;
        else
            usernameAlreadyInDatabase = true;
    }

    public boolean isPasswordsDoNotMatch() {
        return passwordsDoNotMatch;
    }

    public boolean isUsernameAlreadyInDatabase() {
        return usernameAlreadyInDatabase;
    }

}
