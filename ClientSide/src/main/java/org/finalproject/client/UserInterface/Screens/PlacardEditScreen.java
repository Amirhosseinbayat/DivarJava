package org.finalproject.client.UserInterface.Screens;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.DataObject.User;
import org.finalproject.client.UserInterface.BackSupportedInputHandler;
import org.finalproject.client.UserInterface.InputHandler;
import org.finalproject.client.UserInterface.UIUtils;

import java.util.HashMap;
import java.util.Map;

public class PlacardEditScreen extends PlacardCreateScreen {
    private final User user;
    private final Map<Integer, String> idUrlMap = new HashMap<>();
    private final InputHandler imageEditHandler = new BackSupportedInputHandler() {

        @Override
        public boolean handleValidInput(String input) {
            if (input.startsWith("#")) {
                int placardToDelete = Integer.parseInt(input.replace("#", ""));
                String url = idUrlMap.get(placardToDelete);
                if (url == null) {
                    System.out.println("not a meaningful command. try again.");
                    return false;
                }
                getNewCopy().getImageUrlSet().remove(url);
                return trySavePlacard();
            }
            if (input.equalsIgnoreCase("done")) return true;
            getNewCopy().addImageUrl(input);
            trySavePlacard();
            UIUtils.secondary("you can add another image url, or send 'done' to finish adding images.");
            return false;//continue getting input from user.
        }
    };
    private final InputHandler menuHandler = new BackSupportedInputHandler("8") {
        @Override
        public boolean handleValidInput(String input) {
            switch (input) {
                case "1" -> processTitle();
                case "2" -> processAddingImageUrls();
                case "3" -> processDescription();
                case "4" -> processCity();
                case "5" -> processPrice();
                case "6" -> processPhoneNumber();
                case "7" -> processDeletion();
                default -> {
                    return false;
                }
            }
            printMenu();
            return false;//continue getting input from user.
        }
    };

    public PlacardEditScreen(User user, SalePlacard originalPlacard) {
        super(user);
        this.user = user;
        this.originalPlacard = originalPlacard;
        this.editedPlacard = originalPlacard;
    }

    private void processDeletion() {
        //TODO implement.
    }

    @Override
    public void startScreen() {
        printMenu();

        promptInput(menuHandler);
    }

    private void printMenu() {
        UIUtils.header("Placard Edition Page");
        UIUtils.form("1. Title: ", originalPlacard.getTitle());
        UIUtils.form("2. Images: ", originalPlacard.getImageUrlSet().toString());
        UIUtils.form("3. Description: ", originalPlacard.getDescription());
        UIUtils.form("4. City: ", originalPlacard.getCity());
        UIUtils.form("5. Price: ", Long.toString(originalPlacard.getPriceInRials()));
        UIUtils.form("6. Owner's phone number: ", originalPlacard.getPhoneNumber());
        UIUtils.primary("Enter the number of any item to edit it");
        //UIUtils.danger("7. Delete the placard"); not required.
        UIUtils.primary("send 'back' to go back.");
        UIUtils.hr();
    }

    protected void processTitle() {
        promptInput("Enter the new title for your placard:", titleHandler);
    }

    protected void processDescription() {
        promptInput("Write the new description about this placard: "
                , descriptionHandler);
    }

    protected void processPrice() {
        promptInput("Enter the new price in rials: ", priceHandler);
    }

    protected void processCity() {
        UIUtils.primary("enter the new city name for this placard: ");
        if (!user.getCity().isBlank()) {
            UIUtils.secondary(user.getCity()+" (press Enter to continue with your current city or type desired city: )");
        }
        promptInput(cityHandler);

    }

    protected void processAddress() {
        UIUtils.primary("New address related to placard: ");
        promptInput(addressHandler);
    }

    protected void processPhoneNumber() {
        UIUtils.primary("Owner's new phone number: ");
        if (!user.getPhoneNumber().isBlank()) {
            UIUtils.secondary(user.getPhoneNumber()+" (press Enter to continue with your phone number or type desired phone number: )");
        }
        promptInput(phoneHandler);
    }

    protected void processAddingImageUrls() {
        System.out.println("Send a new url to add, or send #N to delete url number N");
        printDeletable();
        promptInput(imageEditHandler);
    }

    void printDeletable() {
        int x = 0;
        for (String url : originalPlacard.getImageUrlSet()) {
            x++;
            idUrlMap.put(x, url);
            UIUtils.form("#"+x+" : ", url);
        }
    }
}
