package pl.pjwstk.jaz.allezon.users;

import java.util.ArrayList;
import java.util.List;

// this should scoped
public class UsersDatabase {
    private static List<User> USERS;

    static {
        USERS = new ArrayList<>();
        USERS.add(new User("Michal","Tangri","admin","admin", "admin@admin.com,", "01-01-1970"));
    }

    public static void addUserToDatabase(User user) {
        USERS.add(user);
    }

    public static boolean checkIfUserExists(String username) {
        for(User user: USERS) {
            if(user.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public static User getUser(String username) {
        for(User user: USERS) {
            if(user.getUsername().equals(username))
                return user;
        }
        return null;
    }

}
