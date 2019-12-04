package pl.pjwstk.jaz.allezon.register;

import pl.pjwstk.jaz.allezon.webapp.AllezonUtils;
import pl.pjwstk.jaz.allezon.entities.ProfileEntity;
import pl.pjwstk.jaz.allezon.repositories.ProfileRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.mindrot.jbcrypt.BCrypt;

@Named
@RequestScoped
public class RegisterController {

    @Inject
    private RegisterRequest registerRequest;
    @Inject
    private ProfileRepository database;

    private boolean passwordsDoNotMatch = false;
    private boolean usernameAlreadyInDatabase = false;

    public void register() {
        System.out.println(System.lineSeparator() + "Tried to register using " + registerRequest.toString());

        if (!database.checkIfUserExists(registerRequest.getUsername()))
            addUserToDatabase();
        else
            usernameAlreadyInDatabase = true;
    }

    private void addUserToDatabase() {
        String password = registerRequest.getPassword();
        String passwordRepeated = registerRequest.getPasswordRepeat();

        if(password.equals(passwordRepeated)) {
            String name = registerRequest.getName();
            String surname = registerRequest.getSurname();
            String username = registerRequest.getUsername();
            String emailAddress = registerRequest.getEmailAddress();
            String dateOfBirth = registerRequest.getDateOfBirth();

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            ProfileEntity user = new ProfileEntity(username, name, surname, hashedPassword, emailAddress, dateOfBirth, false);
            database.addUserToDatabase(user);

            AllezonUtils.redirectToPage("/login.xhtml");
        } else
            passwordsDoNotMatch = true;
    }

    public boolean isPasswordsDoNotMatch() {
        return passwordsDoNotMatch;
    }

    public void setPasswordsDoNotMatch(boolean passwordsDoNotMatch) {
        this.passwordsDoNotMatch = passwordsDoNotMatch;
    }

    public boolean isUsernameAlreadyInDatabase() {
        return usernameAlreadyInDatabase;
    }

}
