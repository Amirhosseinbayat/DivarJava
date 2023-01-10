package org.finalproject.server.Http.RequestHandlers;

import org.finalproject.DataObject.User;
import org.finalproject.server.Database.IDataBase;
import org.finalproject.server.Database.QueryConstraints;
import org.finalproject.server.Http.Request;
import org.finalproject.server.Http.Response;

import java.net.HttpURLConnection;

public class LoginHandler implements RequestHandler {
    IDataBase dataBase; //dependency injection.

    public LoginHandler(IDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Response handle(Request request) throws Exception {
        User user = request.getRequestBody();
        User databaseUser = dataBase.findOne(new QueryConstraints<>() {
            @Override
            public boolean test(User object) {
                if (object.getUsername() == null) return false;
                return object.getUsername().equals(user.getUsername());
            }

            @Override
            public int compare(User o1, User o2) {
                return 0;
            }
        });
        if (databaseUser == null || !databaseUser.getPassword().equals(user.getPassword()))
            return new Response(HttpURLConnection.HTTP_UNAUTHORIZED
                    , "Invalid username / password. try again.");
        return new Response(200, databaseUser);
    }

    @Override
    public String getHandlerCode() {
        return "POST:/login";
    }
}
