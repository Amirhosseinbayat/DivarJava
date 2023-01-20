package org.finalproject.server.Logic;

import org.finalproject.DataObject.User;
import org.finalproject.server.Database.IDataBase;
import org.finalproject.server.Database.QueryConstraints;

import java.io.IOException;

public class UsernameValidator {

    IDataBase dataBase; //dependency injection.

    public UsernameValidator(IDataBase dataBase) {
        this.dataBase = dataBase;
    }

    public String validateUserName(String username) throws IOException {
        User user = dataBase.findOne(new QueryConstraints<>() {
            @Override
            public boolean test(User object) {
                return (username.equalsIgnoreCase(object.getUsername())); //usernames should not be case-sensitive.
            }

            @Override
            public int compare(User o1, User o2) {
                return 0;
            }
        });
        if (user != null) return "This username is already taken!";

        if (!username.matches("^[a-zA-z0-9]+$")) { //^ beginning of the string, $ end of the string.
            return "Username can only contain letters and numbers without space.";
        }
        return null;
    }

}
