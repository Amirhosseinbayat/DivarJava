package org.finalproject.server.Http.RequestHandlers;

import org.finalproject.server.Http.Request;
import org.finalproject.server.Http.Response;
import org.finalproject.server.Logic.UsernameValidator;

import java.net.HttpURLConnection;

/**
 * Will check the uniqueness of a given username,
 * will be used in sign up process when the user is choosing his/her username.
 */
public class UserNameHandler implements RequestHandler {
    UsernameValidator validator = new UsernameValidator();

    @Override
    public Response handle(Request request) throws Exception {
        String username = request.getRequestBody();
        String result = validator.validateUserName(username);
        if (result == null) return new Response(200, "username_ok");
        else return new Response(HttpURLConnection.HTTP_CONFLICT, result);
    }

    @Override
    public String getHandlerCode() {
        return "POST:/username/check";
    }
}
