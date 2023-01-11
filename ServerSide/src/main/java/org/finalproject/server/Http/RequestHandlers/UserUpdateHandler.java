package org.finalproject.server.Http.RequestHandlers;

import org.finalproject.DataObject.User;
import org.finalproject.server.Database.IDataBase;
import org.finalproject.server.Http.Request;
import org.finalproject.server.Http.Response;
import org.finalproject.server.Logic.PasswordValidator;
import org.finalproject.server.Logic.UsernameValidator;

import java.net.HttpURLConnection;
import java.util.Objects;

public class UserUpdateHandler implements RequestHandler {

    final UsernameValidator usernameValidator;
    final PasswordValidator passwordValidator;
    IDataBase dataBase; //dependency injection.

    public UserUpdateHandler(IDataBase dataBase) {
        this.dataBase = dataBase;
        usernameValidator = new UsernameValidator(dataBase);
        passwordValidator = new PasswordValidator();
    }

    @Override
    public Response handle(Request request) throws Exception {
        User user = request.getRequestBody();
        if (request.getUser() == null || request.getUser().getObjectId() != user.getObjectId()) {
            return new Response(HttpURLConnection.HTTP_UNAUTHORIZED, "access denied.");
        }
        User databaseUser = dataBase.getObjectWithId(user.getObjectId());
        if (databaseUser == null) return
                new Response(HttpURLConnection.HTTP_NOT_FOUND, "record not found to update.");
        if (!Objects.equals(databaseUser.getPassword(), user.getPassword())) {
            String passwordResult = passwordValidator.validatePassword(user.getPassword());
            if (passwordResult != null) return new Response(601, passwordResult);
        }
        if (!Objects.equals(databaseUser.getUsername(), user.getUsername())) {
            String nameResult = usernameValidator.validateUserName(user.getUsername());
            if (nameResult != null) return new Response(HttpURLConnection.HTTP_CONFLICT, nameResult);
        }
        dataBase.save(user);
        return new Response(200, user);
    }

    @Override
    public String getHandlerCode() {
        return "POST:/user/update";
    }
}
