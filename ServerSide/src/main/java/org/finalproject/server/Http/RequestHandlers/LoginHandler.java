package org.finalproject.server.Http.RequestHandlers;

import org.finalproject.DataObject.User;
import org.finalproject.server.Database.QueryConstraints;
import org.finalproject.server.Http.Request;
import org.finalproject.server.Http.Response;
import org.finalproject.server.ServerConfiguration;

import java.net.HttpURLConnection;

public class LoginHandler implements RequestHandler {
    @Override
    public Response handle(Request request) throws Exception {
        User user = request.getRequestBody();
        User databaseUser = ServerConfiguration.getInstance().getDataBase().findOne(new QueryConstraints<User>() {
            @Override
            public boolean test(User object) {
                return object.getName().equals(user.getName());
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
