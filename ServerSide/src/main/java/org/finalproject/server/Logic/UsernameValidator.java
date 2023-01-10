package org.finalproject.server.Logic;

import org.finalproject.DataObject.User;
import org.finalproject.server.Database.IDataBase;
import org.finalproject.server.Database.QueryConstraints;

import java.io.IOException;

public class UsernameValidator implements BusinessLogic {

    IDataBase dataBase; //dependency injection.

    public UsernameValidator(IDataBase dataBase) {
        this.dataBase = dataBase;
    }

    public String validateUserName(String username) throws IOException {
        User user = dataBase.findOne(new QueryConstraints<>() {
            @Override
            public boolean test(User object) {
                return (username.equals(object.getUsername()));
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
