package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class PlacardsScreen extends UIScreen {
    HashMap<String, String> filter = new HashMap<String, String>() {{
        put("searchedText", "");
        put("order", "asc");
        put("city", "tehran");
        put("priceMoreThan", "0");
        put("priceLessThan", Long.toString(Long.MAX_VALUE));
    }};
    public PlacardsScreen(Scanner scanner) {
        super(scanner);
    }

    @Override
    void printGuideMessage() {
        UIUtils.header("Placards Page");
        List<SalePlacard> placardList = getTestPlacards();
        int i = 1;
        for (SalePlacard placard : placardList) {
            UIUtils.placardTemplate(i++,(String) placard.getImagesUrl().toArray()[0], placard.getTitle(), placard.getDescription().substring(0, Math.min(placard.getDescription().length(), 60)), placard.getCity(), placard.getPriceInRials(), null);
        }
        UIUtils.hr();
        UIUtils.form("1. Searched text in descriptions: ", filter.get("searchedText"));
        UIUtils.form("2. City: ", filter.get("city"));
        UIUtils.form("3. Order by price: ", filter.get("order"));
        UIUtils.form("4. Price more than: ", filter.get("priceMoreThan"));
        UIUtils.form("5. Price less than: ", filter.get("priceLessThan"));
        UIUtils.hr();
    }

    List<SalePlacard> getTestPlacards() {
        List<SalePlacard> placardList = new ArrayList<>();
        for (int x = 0; x != 10; x++) {
            SalePlacard placard = new SalePlacard("dummy placard number "+x);
            placard.setPriceInRials(410000*(x+1));
            placard.setCity("Tehran");
            placard.setDescription("some kind of description is here. purchase this item!!!");
            placard.setPhoneNumber("+989900229691");
            placard.addImageUrl("sdlkfjskdfj.jpg");
            placardList.add(placard);
        }
        return placardList;
    }

    List<SalePlacard> getPlacards() {
        try {
            Response response = ClientConfiguration.getInstance().getRequestManager()
                    .sendRequest(new Request("GET", "placards/list"));
            List<SalePlacard> placardList = response.getResponseBody();
            System.out.println("retrieved "+placardList.size()+" placards:");
            return placardList;
        } catch (RequestException e) {
            e.printDetails();
        }
        return new ArrayList<>();
    }

    //TODO Implementation of filter by city, filter by search in
    // description and sort and filter by price

    @Override
    void processInput() {
        String input = scanner.nextLine();
    }
}
