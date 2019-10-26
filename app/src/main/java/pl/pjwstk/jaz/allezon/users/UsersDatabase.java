package pl.pjwstk.jaz.allezon.users;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UsersDatabase {
    private List<User> users = new ArrayList<>();

    public void addUserToDatabase(User user) {
        users.add(user);
    }

    public boolean checkIfUserExists(String username) {
        for(User user: users) {
            if(user.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public User getUser(String username) {
        for(User user: users) {
            if(user.getUsername().equals(username))
                return user;
        }
        return null;
    }

}
