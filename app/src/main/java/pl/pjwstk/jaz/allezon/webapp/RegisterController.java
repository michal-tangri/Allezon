package pl.pjwstk.jaz.allezon.webapp;

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

    private boolean passwordsDoNotMatch = false;

    public void register() {
        System.out.println("Tried to register using " + registerRequest.toString());
        if(!UsersDatabase.checkIfUserExists(registerRequest.getUsername()))
            addUserToDatabase();
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
            User registeringUser = new User(name, surname, username, password, emailAddress, dateOfBirth);
            UsersDatabase.addUserToDatabase(registeringUser);

            //https://stackoverflow.com/questions/16776981/response-object-in-jsf
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            try {
                externalContext.redirect("http://localhost:8080/app/login.xhtml");
            } catch (IOException exception0) {
                System.out.println("Failed to redirect to index.xhtml");
            }
        }
        else
            passwordsDoNotMatch = true;



    }

    public boolean isPasswordsDoNotMatch() {
        return passwordsDoNotMatch;
    }

    public void setPasswordsDoNotMatch(boolean passwordsDoNotMatch) {
        this.passwordsDoNotMatch = passwordsDoNotMatch;
    }
}
