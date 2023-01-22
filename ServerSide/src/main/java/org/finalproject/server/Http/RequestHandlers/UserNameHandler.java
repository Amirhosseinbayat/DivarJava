package org.finalproject.server.Http.RequestHandlers;

import org.finalproject.server.Database.IDataBase;
import org.finalproject.server.Http.Request;
import org.finalproject.server.Http.Response;
import org.finalproject.server.Logic.UsernameValidator;

import java.net.HttpURLConnection;

/**
 * Will check the uniqueness of a given username,
 * will be used in sign up process when the user is choosing his/her username.
 */
public class UserNameHandler implements RequestHandler {
    final UsernameValidator validator;
    final IDataBase dataBase; //dependency injection.

    public UserNameHandler(IDataBase dataBase) {
        this.dataBase = dataBase;
        validator = new UsernameValidator(dataBase);
    }

    @Override
    public Response handle(Request request) throws Exception {
        String username = request.getBodyObject();
        String result = validator.validateUserName(username);
        if (result == null) return new Response(200, "username_ok");
        else return new Response(HttpURLConnection.HTTP_CONFLICT, result);
    }

    @Override
    public String getHandlerCode() {
        return "POST:/username/check";
    }
}
