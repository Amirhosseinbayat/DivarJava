package org.finalproject.server.Http.RequestHandlers;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.DataObject.User;
import org.finalproject.server.Database.IDataBase;
import org.finalproject.server.Http.Request;
import org.finalproject.server.Http.Response;
import org.finalproject.server.Logic.PlacardValidator;

import java.net.HttpURLConnection;

public class PlacardSaveHandler implements RequestHandler {

    final IDataBase dataBase; //dependency injection.

    final PlacardValidator placardValidator;

    public PlacardSaveHandler(IDataBase dataBase) {
        this.dataBase = dataBase;
        placardValidator = new PlacardValidator();
    }

    @Override
    public Response handle(Request request) throws Exception {
        SalePlacard salePlacard = request.getBodyObject();
        User user = request.getUser();
        if (user == null) return new Response(HttpURLConnection.HTTP_UNAUTHORIZED
                , "you need to signUp/logIn to create a placard.");
        SalePlacard original = null;
        if (salePlacard.getObjectId() != -1) {
            original = dataBase.getObjectWithId(salePlacard.getObjectId());
            if (original == null) return new Response(HttpURLConnection.HTTP_NOT_FOUND, "placard not found.");
            if (!original.isCreatedBy(user)) {
                return new Response(HttpURLConnection.HTTP_UNAUTHORIZED
                        , "You are not authorized to edit this placard.");
            }
        }
        String validationResult = PlacardValidator.validate(salePlacard, original);
        if (validationResult != null) return new Response(HttpURLConnection.HTTP_BAD_REQUEST
                , validationResult);
        salePlacard.setCreatedBy(user.getObjectId()); //ensures createdBy is set to this user (prevent hack)
        dataBase.save(salePlacard);
        user.addToCreatedPlacards(salePlacard.getObjectId());
        dataBase.save(user); //updates user.
        return new Response(200, salePlacard);
    }

    @Override
    public String getHandlerCode() {
        return "POST:/placard/";
    }
}
