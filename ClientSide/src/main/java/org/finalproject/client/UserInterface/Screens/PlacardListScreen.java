package org.finalproject.client.UserInterface.Screens;

import org.finalproject.DataObject.PlacardQuery;
import org.finalproject.DataObject.SalePlacard;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;
import org.finalproject.client.UserInterface.*;

import java.util.ArrayList;
import java.util.List;

public class PlacardListScreen extends UIScreen {

    protected List<SalePlacard> placardList;
    private final PlacardQuery placardQuery;

    public PlacardListScreen() {
        placardQuery = new PlacardQuery();
        placardQuery.setCity(ClientConfiguration.getInstance().getUser().getCity());
    }

    private final InputHandler priceMinHandler = new CancelSupportedHandler("back") {
        @Override
        protected void onCancel() {
            UIUtils.clearScreen();
            printPlacards();
            printQuery();
        }

        @Override
        public boolean handleValidInput(String input) {
            long min;
            try {
                if (input.isEmpty() || input.equals("\n")) {
                    min = 0;
                } else min = Long.parseLong(input);
            } catch (Exception e) { //numberFormat or too long input.
                UIUtils.danger("you need to enter an integer number.");
                return false;
            }
            if (min<0) {
                UIUtils.danger("a price can not be less than zero.");
                return false;
            }
            if (placardQuery.getPriceLessThan()<min) {
                UIUtils.danger("min can not be more than max! try again with a value less than "
                        +placardQuery.getPriceLessThan());
                return false;
            }
            placardQuery.setPriceGreaterThan(min);
            UIUtils.clearScreen();
            printPlacards();
            printQuery();
            return true;
        }
    };
    private final InputHandler sortHandler = new CancelSupportedHandler("") {
        @Override
        public boolean handleValidInput(String input) {
            switch (input) {
                case "1" -> placardQuery.setOrderBy(PlacardQuery.ORDER_BY_CREATION_DESC);
                case "2" -> placardQuery.setOrderBy(PlacardQuery.ORDER_BY_CREATION_ASC);
                case "3" -> placardQuery.setOrderBy(PlacardQuery.ORDER_BY_UPDATE_DESC);
                case "4" -> placardQuery.setOrderBy(PlacardQuery.ORDER_BY_UPDATE_ASC);
                case "5" -> placardQuery.setOrderBy(PlacardQuery.ORDER_BY_PRICE_DESC);
                case "6" -> placardQuery.setOrderBy(PlacardQuery.ORDER_BY_PRICE_ASC);
                default -> {
                    UIUtils.danger(input+" is not meaningful here...");
                    return false;
                }
            }
            UIUtils.clearScreen();
            printPlacards();
            printQuery();
            return true;
        }

        @Override
        protected void onCancel() {
            UIUtils.clearScreen();
            printPlacards();
            printQuery();
        }
    };
    private final InputHandler priceMaxHandler = new CancelSupportedHandler() {
        @Override
        protected void onCancel() {
            placardQuery.setSearchText("");
            UIUtils.clearScreen();
            printPlacards();
            printQuery();
        }

        @Override
        public boolean handleValidInput(String input) {
            long max;
            try {
                if (input.isEmpty() || input.equals("\n")) {
                    max = Long.MAX_VALUE;
                } else max = Long.parseLong(input);
            } catch (Exception e) { //numberFormat or too long input.
                UIUtils.danger("you need to enter an integer number.");
                return false;
            }
            if (max<0) {
                UIUtils.danger("a price can not be less than zero.");
                return false;
            }
            placardQuery.setPriceLessThan(max);
            return true;
        }
    };
    private final InputHandler searchHandler = new CancelSupportedHandler("") {
        @Override
        protected void onCancel() {
            UIUtils.clearScreen();
            printPlacards();
            printQuery();
        }

        @Override
        public boolean handleValidInput(String input) {
            placardQuery.setSearchText(input);
            UIUtils.clearScreen();
            printPlacards();
            printQuery();
            return true;
        }
    };
    private final InputHandler cityHandler = new CancelSupportedHandler("\n") {
        @Override
        protected void onCancel() {
            UIUtils.clearScreen();
            printPlacards();
            printQuery();
        }

        @Override
        public boolean handleValidInput(String input) {
            placardQuery.setCity(input);
            UIUtils.clearScreen();
            printPlacards();
            printQuery();
            return true;
        }
    };

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
        promptInput(sortHandler);
    }

    void printQuery() {
        UIUtils.hr();
        UIUtils.secondary("Enter the number of any query criteria to edit it.");
        UIUtils.form("1. Placards containing the text: ", placardQuery.getSearchText());
        UIUtils.form("2. City: ", placardQuery.getCity() != null ? placardQuery.getCity() : "");
        UIUtils.form("3. Sort: ", placardQuery.getOrderByHumanReadable());
        UIUtils.form("4. Price Range: ", placardQuery.getPriceRange());
        UIUtils.secondary("Press Enter to fetch results | send #N to select placard N | send 'back' to go back ");
    }

    void printPlacards() {
        if (placardList == null) return;
        int i = 1;
        for (SalePlacard placard : placardList) {
            UIUtils.placardTemplate(i++, placard, true);
        }
    }

    void processCityName() {
        UIUtils.primary("Enter the name of the city you want to see its placards");
        UIUtils.secondary("Or press Enter to reset city and go back.");
        promptInput(cityHandler);
    }

    private void processPriceRange() {
        UIUtils.primary("Let's define your budget. please enter the maximum you can pay");
        UIUtils.secondary("Press enter to set no maximum, send 'back' to go back");
        promptInput(priceMaxHandler);
        UIUtils.secondary("now enter the minimum or press enter to leave it empty."+
                "\nsend 'back' to go back");
        promptInput(priceMinHandler);

    }

    void processSearchText() {
        UIUtils.primary("Enter the query text");
        UIUtils.secondary("Or press Enter to reset it and go back.");
        promptInput(searchHandler);

    }

    @Override
    public void startScreen() {
        UIUtils.clearScreen();
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

                    default -> {
                        if (input.isBlank()) {
                            UIUtils.clearScreen();
                            UIUtils.header("Placards Page");
                            placardList = getPlacards(placardQuery);
                            printPlacards();
                            printQuery();
                        } else {
                            if (input.startsWith("#")) {
                                try {
                                    int placardIndex = Integer.parseInt(input.replace("#", ""))-1;
                                    if (placardIndex>=placardList.size()) {
                                        UIUtils.danger("there is no placard with index "+placardIndex);
                                        return false;
                                    }
                                    SalePlacard placard = placardList.get(placardIndex);
                                    Navigation.navigateTo(new PlacardDetailsScreen(placard));
                                    return true;
                                } catch (Exception e) {
                                    UIUtils.danger("you have to enter an integer in a valid range.");
                                    return false;
                                }
                            }
                            UIUtils.danger(input+" is not a meaningful command here.");
                        }
                    }
                }
                return false; //continue getting input from user.
            }
        });
    }

    @Override
    public void trimMemory() {
        if (placardList != null) placardList.clear();
        //no need to do a clear when we set the list to null but just in case:)
        placardList = null;
    }
}
