package org.finalproject.server.Http.RequestHandlers;

import org.finalproject.DataObject.CityDetails;
import org.finalproject.DataObject.SalePlacard;
import org.finalproject.server.Database.IDataBase;
import org.finalproject.server.Database.QueryConstraints;
import org.finalproject.server.Http.Request;
import org.finalproject.server.Http.Response;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class GetAllCitiesHandler implements RequestHandler {

    IDataBase dataBase;

    public GetAllCitiesHandler(IDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Response handle(Request request) throws Exception {
        List<SalePlacard> placardList = dataBase.findAll(new QueryConstraints<>() {
            @Override
            public boolean test(SalePlacard object) {
                return object.getPhoneNumber() != null;//a placard is not shown until it has a phone number.
            }

            @Override
            public int compare(SalePlacard o1, SalePlacard o2) {
                return 0;
            }
        });
        Hashtable<String,CityDetails> cityDetailsHashtable = new Hashtable<>();
        for (SalePlacard placard : placardList){
            String cityName = placard.getCity().toLowerCase();
            CityDetails cityDetails = cityDetailsHashtable.get(cityName);
            if (cityDetails==null){
                cityDetails = new CityDetails();
                cityDetails.setName(cityName);
                cityDetailsHashtable.put(cityName,cityDetails);
            }
            cityDetails.incrementCount();
        }
        return new Response(200,new ArrayList<>(cityDetailsHashtable.values()));
    }

    @Override
    public String getHandlerCode() {
        return "GET:/placard/cities";
    }
}
