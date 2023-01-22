package org.finalproject.server.Http.RequestHandlers;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.server.Database.IDataBase;
import org.finalproject.server.Http.Request;
import org.finalproject.server.Http.Response;

import java.net.HttpURLConnection;

public class PromotePlacardHandler implements RequestHandler {

    IDataBase dataBase;

    public PromotePlacardHandler(IDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Response handle(Request request) throws Exception {
        if (request.getUser()==null)return new Response(HttpURLConnection.HTTP_UNAUTHORIZED,"login first");
        String body = request.getBodyObject();
        long id = Long.parseLong(body);
        SalePlacard placard = dataBase.getObjectWithId(id);
        if (placard==null)return new Response(404,"not found");
        if (!placard.isCreatedBy(request.getUser()))return new Response(403,
                "you can not promote a placard you haven't created.");

        placard.setPromotionExpireData(System.currentTimeMillis()+5*60*1000);
        dataBase.save(placard);

        return new Response(HttpURLConnection.HTTP_OK,placard);
    }

    @Override
    public String getHandlerCode() {
        return "POST:/placard/promote";
    }
}
