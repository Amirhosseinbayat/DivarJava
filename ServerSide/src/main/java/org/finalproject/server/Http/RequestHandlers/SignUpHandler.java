package org.finalproject.server.Http.RequestHandlers;

import org.finalproject.DataObject.User;
import org.finalproject.server.Http.Request;
import org.finalproject.server.Http.Response;
import org.finalproject.server.Logic.PasswordValidator;
import org.finalproject.server.Logic.UsernameValidator;
import org.finalproject.server.ServerConfiguration;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Will create a new user.
 */
@SuppressWarnings("DuplicatedCode")
public class SignUpHandler implements RequestHandler {

    UsernameValidator usernameValidator = new UsernameValidator();
    PasswordValidator passwordValidator = new PasswordValidator();

    @Override
    public Response handle(Request request) throws IOException {
        User user = request.getRequestBody();
        String nameResult = usernameValidator.validateUserName(user.getUsername());
        if (nameResult != null) return new Response(HttpURLConnection.HTTP_CONFLICT, nameResult);
        String passwordResult = passwordValidator.validatePassword(user.getPassword());
        if (passwordResult != null) return new Response(601, passwordResult);
        ServerConfiguration.getInstance().getDataBase().save(user);
        return new Response(200, user);
    }

    @Override
    public String getHandlerCode() {
        return "POST:/signup";
    }
}
