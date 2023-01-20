package org.finalproject.client.UserInterface.Screens;

import org.finalproject.DataObject.PlacardQuery;
import org.finalproject.DataObject.SalePlacard;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;
import org.finalproject.client.UserInterface.BackSupportedInputHandler;
import org.finalproject.client.UserInterface.Navigation;
import org.finalproject.client.UserInterface.UIScreen;
import org.finalproject.client.UserInterface.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class PlacardListScreen extends UIScreen {

    PlacardQuery placardQuery;

    protected List<SalePlacard> placardList;

    public PlacardListScreen() {
        placardQuery = new PlacardQuery();
        placardQuery.setCity(ClientConfiguration.getInstance().getUser().getCity());
    }

    void printQuery() {
        UIUtils.hr();
        UIUtils.secondary("Enter the number of any query criteria to edit it.");
        UIUtils.form("1. Placards containing the text: ", placardQuery.getSearchText());
        UIUtils.form("2. City: ", placardQuery.getCity());
        UIUtils.form("3. Sort: ", placardQuery.getOrderByHumanReadable());
        UIUtils.form("4. Price Range: ", placardQuery.getPriceRange());
        UIUtils.secondary("Press Enter to fetch results | send #N to select placard N | send 'back' to go back ");
    }

    void printPlacards() {
        int i = 1;
        for (SalePlacard placard : placardList) {
            UIUtils.placardTemplate(i++, placard,true);
        }
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

    List<SalePlacard> getPlacards(PlacardQuery query) {
        try {
            Response response = ClientConfiguration.getInstance().getRequestManager()
                    .sendRequest(new Request("POST", "placard/query")
                            .setBody(query));
            List<SalePlacard> placardList = response.getResponseBody();
            System.out.println("retrieved "+placardList.size()+" placards:");
            return placardList;
        } catch (RequestException e) {
            e.printDetails();
        }
        return new ArrayList<>();
    }


    @Override
    public void startScreen() {
        UIUtils.header("Placards Page");
        UIUtils.primary("What kind of placards are you looking for?");
        printQuery();
        promptInput(new BackSupportedInputHandler() {
            @Override
            public boolean handleValidInput(String input) {
                switch (input) {
                    case "1" -> processSearchText();
                    case "2" -> processCityName();
                    case "3" -> processSort();
                    case "4" -> processPriceRange();
                }
                if (input.startsWith("#")) {
                    int placardIndex = Integer.parseInt(input.replace("#", ""))-1;
                    SalePlacard placard = placardList.get(placardIndex);
                    Navigation.navigateTo(new PlacardDetailsScreen(placard));
                    return true;
                }
                if (input.isEmpty() || input.equals("\n")) {
                    placardList = getPlacards(placardQuery);
                    printPlacards();
                    printQuery();
                    return false; //continue getting input from user.
                }
                return false;
            }
        });
    }

    private void processPriceRange() {
        UIUtils.primary("Let's define your budget. please enter the maximum you can pay");
        UIUtils.secondary("Press enter to set no maximum, send 'back' to go back");
        long max;
        while (true) {
            try {
                String input = scanner.nextLine();
                if (input.isEmpty() || input.equals("\n")) {
                    max = Long.MAX_VALUE;
                    break;
                }
                if (input.equals("back")) {
                    printQuery();
                    startScreen();
                    return;
                }
                max = Long.parseLong(input);
                break;
            } catch (NumberFormatException e) {
                UIUtils.danger("you need to enter an integer number.");
            }
        }
        long min;
        UIUtils.secondary("Ok. now enter the minimum price of a placard you would be interested in.");
        while (true) {
            try {
                String input = scanner.nextLine();
                if (input.isEmpty() || input.equals("\n")) {
                    min = 0;
                    break;
                }
                if (input.equals("back")) {
                    printQuery();
                    startScreen();
                    return;
                }
                min = Long.parseLong(input);
                break;
            } catch (NumberFormatException e) {
                UIUtils.danger("you need to enter an integer number.");
            }
        }
        //TODO show error when max<min
        placardQuery.setPriceLessThan(max);
        placardQuery.setPriceGreaterThan(min);
        printQuery();
        startScreen();
    }

    private void processSort() {
        UIUtils.primary("Select the sort mechanism of your choice:");
        UIUtils.options(
                PlacardQuery.getOrderByHumanReadable(PlacardQuery.ORDER_BY_CREATION_DESC),
                PlacardQuery.getOrderByHumanReadable(PlacardQuery.ORDER_BY_CREATION_ASC),
                PlacardQuery.getOrderByHumanReadable(PlacardQuery.ORDER_BY_UPDATE_DESC),
                PlacardQuery.getOrderByHumanReadable(PlacardQuery.ORDER_BY_UPDATE_ASC),
                PlacardQuery.getOrderByHumanReadable(PlacardQuery.ORDER_BY_PRICE_DESC),
                PlacardQuery.getOrderByHumanReadable(PlacardQuery.ORDER_BY_PRICE_ASC)
        );
        UIUtils.secondary("Or press Enter to go back.");
        String input = scanner.nextLine();
        if (input.isEmpty() || input.equals("\n")) {
            printQuery();
            startScreen();
            return;
        }
        switch (input) {
            case "1" -> placardQuery.setOrderBy(PlacardQuery.ORDER_BY_CREATION_DESC);
            case "2" -> placardQuery.setOrderBy(PlacardQuery.ORDER_BY_CREATION_ASC);
            case "3" -> placardQuery.setOrderBy(PlacardQuery.ORDER_BY_UPDATE_DESC);
            case "4" -> placardQuery.setOrderBy(PlacardQuery.ORDER_BY_UPDATE_ASC);
            case "5" -> placardQuery.setOrderBy(PlacardQuery.ORDER_BY_PRICE_DESC);
            case "6" -> placardQuery.setOrderBy(PlacardQuery.ORDER_BY_PRICE_ASC);
            default -> {
                UIUtils.danger(input+" is not meaningful here...");
            }
        }
        printQuery();
        startScreen();
    }

    void processSearchText() {
        UIUtils.primary("Enter the query text");
        UIUtils.secondary("Or press Enter to go back.");
        String input = scanner.nextLine();
        if (input.isEmpty() || input.equals("\n")) {
            printQuery();
            startScreen();
            return;
        }
        placardQuery.setSearchText(input);
        printQuery();
        startScreen();
    }

    void processCityName() {
        UIUtils.primary("Enter the name of the city you want to see its placards");
        UIUtils.secondary("Or press Enter to go back.");
        String input = scanner.nextLine();
        if (input.isEmpty() || input.equals("\n")) {
            printQuery();
            startScreen();
            return;
        }
        placardQuery.setCity(input);
        printQuery();
        startScreen();
    }


}
