package pl.pjwstk.jaz.allezon.users;

public class User {
    private String name;
    private String surname;
    private String password;
    private String username;
    private String emailAddress;
    private String dateOfBirth;

    public User(String name, String surname, String username, String password, String emailAddress, String dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.username = username;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getName() {
        return name;
    }

}
