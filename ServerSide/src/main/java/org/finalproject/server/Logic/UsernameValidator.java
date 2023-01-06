package org.finalproject.server.Logic;

import org.finalproject.DataObject.User;
import org.finalproject.server.Database.QueryConstraints;
import org.finalproject.server.ServerConfiguration;

import java.io.IOException;

public class UsernameValidator implements BusinessLogic {

    public String validateUserName(String username) throws IOException {
        User user = ServerConfiguration.getInstance().getDataBase().findOne(new QueryConstraints<User>() {
            @Override
            public boolean test(User object) {
                return (object.getName().equals(username));
            }

            @Override
            public int compare(User o1, User o2) {
                return 0;
            }
        });
        if (user == null) return null;
        else return "این نام کاربری قبلا گرفته شده است.";
    }

}