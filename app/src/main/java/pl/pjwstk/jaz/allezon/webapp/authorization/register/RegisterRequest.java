package pl.pjwstk.jaz.allezon.webapp.authorization.register;

import org.mindrot.jbcrypt.BCrypt;
import pl.pjwstk.jaz.allezon.webapp.authorization.entities.ProfileEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class RegisterRequest {

    private String name;
    private String surname;
    private String password;
    private String passwordRepeated;
    private String username;
    private String emailAddress;
    private String dateOfBirth;

    public ProfileEntity toProfileEntity() {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        return new ProfileEntity(username, name, surname, hashedPassword, emailAddress, dateOfBirth, false);
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", DoB='" + dateOfBirth + '\'' +
                '}';
    }

    public boolean doPasswordsMatch() {
        return password.equals(passwordRepeated);
    }

    //Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordRepeated() {
        return passwordRepeated;
    }

    public void setPasswordRepeated(String passwordRepeated) {
        this.passwordRepeated = passwordRepeated;
    }
}