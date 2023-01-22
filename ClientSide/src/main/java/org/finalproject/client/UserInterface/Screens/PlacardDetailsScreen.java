package org.finalproject.client.UserInterface.Screens;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.DataObject.User;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.IHttpRequestManager;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;
import org.finalproject.client.UserInterface.BackSupportedInputHandler;
import org.finalproject.client.UserInterface.Navigation;
import org.finalproject.client.UserInterface.UIScreen;
import org.finalproject.client.UserInterface.UIUtils;

public class PlacardDetailsScreen extends UIScreen {
    SalePlacard placard;
    User user;

    public PlacardDetailsScreen(SalePlacard placard) {
        this.placard = placard;
        this.user = ClientConfiguration.getInstance().getUser();
    }

    @Override
    public void startScreen() {
        UIUtils.header("Placard Details");
        UIUtils.placardTemplate(0, placard, false);
        UIUtils.hr();
        if (placard.isCreatedBy(user)) {
            UIUtils.primary("this placard is created by you! you can't add it to your wish list, but you can edit it.");
            UIUtils.secondary("1. Edit placard details");
            if (!placard.isStillPromoted()) UIUtils.secondary("2. Make it special");
        } else {
            UIUtils.options(isUserWish() ? "Remove from wish list" : "Add to wish list");
        }
        UIUtils.secondary("Type 'back' to go back.");

        promptInput(new BackSupportedInputHandler() {
            @Override
            public boolean handleValidInput(String input) {
                switch (input) {
                    case "1" -> {
                        if (placard.isCreatedBy(user)) {
                            Navigation.navigateTo(new PlacardEditScreen(user, placard));
                        } else {
                            toggleWishStatus();
                        }
                    }
                    case "2" -> {
                        if (!placard.isStillPromoted()) {
                            Navigation.navigateTo(new PaymentScreen(placard));
                        } else {
                            return false;
                        }
                    }
                    case "back" -> Navigation.popBackStack();
                    default -> {
                        System.out.println(input+" is not a meaningful command in this context.");
                        return false;
                    }
                }
                return true;
            }
        });
    }

    private boolean isUserWish(){
        //TODO check placard objectId whether is in the user wish list
        return false;
    }

    private void toggleWishStatus() {
        if (isUserWish()) user.removeFromCreatedPlacards(placard.getObjectId());
        else user.addToLikedPlacards(placard.getObjectId());
        trySaveUserObject("The placard "+(isUserWish() ? "removed from" : "added to")+" your wish list successfully");
        UIUtils.successful("(press Enter to continue: )");
        scanner.nextLine();
        startScreen();
    }

    void trySaveUserObject(String message) {
        IHttpRequestManager manager = ClientConfiguration.getInstance().getRequestManager();
        try {
            Response response =
                    manager.sendRequest(new Request("POST", "user/update").setBody(user));
            user = response.getResponseBody();
            ClientConfiguration.getInstance().setUser(user);
        } catch (RequestException e) {
            UIUtils.danger("Something went wrong while trying to update. "+ e.getMessage());
            return;
        }
        UIUtils.successful(message);
    }
}
