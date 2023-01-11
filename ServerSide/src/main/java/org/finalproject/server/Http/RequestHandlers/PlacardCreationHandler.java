package org.finalproject.server.Http.RequestHandlers;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.DataObject.User;
import org.finalproject.server.Database.IDataBase;
import org.finalproject.server.Http.Request;
import org.finalproject.server.Http.Response;

import java.net.HttpURLConnection;

public class PlacardCreationHandler implements RequestHandler {

    IDataBase dataBase; //dependency injection.

    public PlacardCreationHandler(IDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Response handle(Request request) throws Exception {
        SalePlacard salePlacard = request.getRequestBody();
        User user = request.getUser();
        if (user == null) return new Response(HttpURLConnection.HTTP_UNAUTHORIZED
                , "you need to signUp/logIn to create a placard.");
        if (salePlacard.getObjectId() != -1) {
            if (user.getObjectId() != -1) return new Response(HttpURLConnection.HTTP_CONFLICT, "Security exception. "+
                    "Do not try to hack us!"); //avoids setting objectId on creation and overwriting another placard.
        }

        salePlacard.setCreatedBy(user.getObjectId()); //ensures createdBy is set to this user (prevent hack)
        dataBase.save(salePlacard);
        user.addToCreatedPlacards(salePlacard.getObjectId());
        dataBase.save(user); //updates user.
        return new Response(200, salePlacard);
    }

    @Override
    public String getHandlerCode() {
        return "POST:/placard/new";
    }
}
