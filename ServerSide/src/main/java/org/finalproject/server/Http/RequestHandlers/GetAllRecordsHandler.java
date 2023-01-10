package org.finalproject.server.Http.RequestHandlers;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.server.Database.IDataBase;
import org.finalproject.server.Database.QueryConstraints;
import org.finalproject.server.Http.Request;
import org.finalproject.server.Http.Response;

import java.util.List;

public class GetAllRecordsHandler implements RequestHandler {
    IDataBase dataBase; //dependency injection.

    public GetAllRecordsHandler(IDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Response handle(Request request) throws Exception {

        List<SalePlacard> salePlacardList = dataBase.findAll(new QueryConstraints<>() {
            @Override
            public boolean test(SalePlacard object) {
                return true;
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
        return "GET:/records/all";
    }
}
