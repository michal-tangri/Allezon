package pl.pjwstk.jaz.allezon.webapp;

import org.mindrot.jbcrypt.BCrypt;
import pl.pjwstk.jaz.allezon.auth.ProfileEntity;
import pl.pjwstk.jaz.allezon.auth.ProfileRepository;
import pl.pjwstk.jaz.allezon.register.RegisterRequest;
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
public class RegisterController {
    @Inject
    private RegisterRequest registerRequest;
    @Inject
    private UsersDatabase localDatabase;
    @Inject
    private ProfileRepository database;

    private boolean passwordsDoNotMatch = false;
    private boolean usernameIsAlreadyInDatabase = false;

    public void register() {
        System.out.println("Tried to register using " + registerRequest.toString());
        if (!localDatabase.checkIfUserExists(registerRequest.getUsername())
                && !database.checkIfUserExists(registerRequest.getUsername())) {
            addUserToLocalDatabase();
            addUserToDatabase();
        }
        else
            usernameIsAlreadyInDatabase = true;

    }

    private void addUserToLocalDatabase() {

        String name = registerRequest.getName();
        String surname = registerRequest.getSurname();
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String passwordRepeat = registerRequest.getPasswordRepeat();
        String emailAddress = registerRequest.getEmailAddress();
        String dateOfBirth = registerRequest.getDateOfBirth();

        if (password.equals(passwordRepeat)) {
            String hashedPass = BCrypt.hashpw(password, BCrypt.gensalt());
            User registeringUser = new User(name, surname, username, hashedPass, emailAddress, dateOfBirth);
            localDatabase.addUserToDatabase(registeringUser);
        } else
            passwordsDoNotMatch = true;
    }

    private void addUserToDatabase() {

        String name = registerRequest.getName();
        String surname = registerRequest.getSurname();
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String passwordRepeat = registerRequest.getPasswordRepeat();
        String emailAddress = registerRequest.getEmailAddress();
        String dateOfBirth = registerRequest.getDateOfBirth();


        if(password.equals(passwordRepeat)) {
            String hashedPass = BCrypt.hashpw(password, BCrypt.gensalt());
            ProfileEntity user = new ProfileEntity(username, name, surname, hashedPass, emailAddress, dateOfBirth);
            database.addUserToDatabase(user);
            redirectToLogin();
        } else
            passwordsDoNotMatch = true;

    }

    private void redirectToLogin() {
        //https://stackoverflow.com/questions/16776981/response-object-in-jsf
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getApplicationContextPath() + "/login.xhtml");
        } catch (IOException exception0) {
            System.out.println("Failed to redirect to login.xhtml");
        }
    }

    public boolean isPasswordsDoNotMatch() {
        return passwordsDoNotMatch;
    }

    public void setPasswordsDoNotMatch(boolean passwordsDoNotMatch) {
        this.passwordsDoNotMatch = passwordsDoNotMatch;
    }

    public boolean isUsernameIsAlreadyInDatabase() {
        return usernameIsAlreadyInDatabase;
    }

}
