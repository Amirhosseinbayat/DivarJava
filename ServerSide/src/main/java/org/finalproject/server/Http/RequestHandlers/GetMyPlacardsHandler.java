package org.finalproject.server.Http.RequestHandlers;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.server.Database.IDataBase;
import org.finalproject.server.Database.QueryConstraints;
import org.finalproject.server.Http.Request;
import org.finalproject.server.Http.Response;

import java.net.HttpURLConnection;
import java.util.List;

public class GetMyPlacardsHandler implements RequestHandler {
    final IDataBase dataBase; //dependency injection.

    public GetMyPlacardsHandler(IDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Response handle(Request request) throws Exception {
        if (request.getUser() == null) return new Response(HttpURLConnection.HTTP_UNAUTHORIZED,
                "you need to login before trying to get your placards.");

        List<SalePlacard> salePlacardList = dataBase.findAll(new QueryConstraints<>() {
            @Override
            public boolean test(SalePlacard object) {
                return object.isCreatedBy(request.getUser());
            }

            @Override
            public int compare(SalePlacard o1, SalePlacard o2) {
                return 0;
            }
        });
        return new Response(200, salePlacardList);
    }

    @Override
    public String getHandlerCode() {
        return "GET:/placard/mine";
    }
}
