package org.finalproject.server.Http.RequestHandlers;

import org.finalproject.DataObject.PlacardQuery;
import org.finalproject.DataObject.SalePlacard;
import org.finalproject.server.Database.IDataBase;
import org.finalproject.server.Database.QueryConstraints;
import org.finalproject.server.Http.Request;
import org.finalproject.server.Http.Response;

import java.util.Comparator;
import java.util.List;

public class QueryPlacardsHandler implements RequestHandler {

    IDataBase dataBase;

    public QueryPlacardsHandler(IDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Response handle(Request request) throws Exception {
        PlacardQuery query = request.getBodyObject(); //standard is to use GET for queries,
        // but we use POST to be able to get Query Object from request body.
        List<SalePlacard> list = dataBase.findAll(new QueryConstraints<>() {
            @Override
            public boolean test(SalePlacard object) {
                if (!query.getSearchText().isEmpty()) {
                    boolean titleMatches = object.getTitle().contains(query.getSearchText());
                    boolean descriptionMatches = object.getDescription().contains(query.getSearchText());
                    if (!titleMatches && !descriptionMatches) return false;
                }
                boolean priceRangeOK = object.getPriceInRials()>=query.getPriceGreaterThan() &&
                        object.getPriceInRials()<=query.getPriceLessThan();
                if (!priceRangeOK) return false;
                return (query.getCity() == null || query.getCity().isEmpty()) || query.getCity().equals(object.getCity());
            }

            @Override
            public int compare(SalePlacard o1, SalePlacard o2) {
                Comparator<SalePlacard> comparator = getComparator(query.getOrderBy());
                if (comparator != null) return comparator.compare(o1, o2);
                return 0;
            }
        });
        return new Response(200, list);
    }

    Comparator<SalePlacard> getComparator(String sortType) {
        switch (sortType) {
            case PlacardQuery.ORDER_BY_PRICE_ASC -> {
                return (o1, o2) -> +Long.compare(o1.getPriceInRials(), o2.getPriceInRials());
            }
            case PlacardQuery.ORDER_BY_PRICE_DESC -> {
                return (o1, o2) -> -Long.compare(o1.getPriceInRials(), o2.getPriceInRials());
            }
            case PlacardQuery.ORDER_BY_UPDATE_ASC -> {
                return (o1, o2) -> +Long.compare(o1.getUpdatedAt(), o2.getUpdatedAt());
            }
            case PlacardQuery.ORDER_BY_UPDATE_DESC -> {
                return (o1, o2) -> -Long.compare(o1.getUpdatedAt(), o2.getUpdatedAt());
            }
            case PlacardQuery.ORDER_BY_CREATION_ASC -> {
                return (o1, o2) -> +Long.compare(o1.getCreatedAt(), o2.getCreatedAt());
            }
            case PlacardQuery.ORDER_BY_CREATION_DESC -> {
                return (o1, o2) -> -Long.compare(o1.getCreatedAt(), o2.getCreatedAt());
            }
        }
        return null;
    }

    @Override
    public String getHandlerCode() {
        return "POST:/placard/query";
    }
}
