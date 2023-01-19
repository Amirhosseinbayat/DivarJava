package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.DataObject.User;
import org.finalproject.client.ClientConfiguration;
import org.finalproject.client.Http.IHttpRequestManager;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;

import java.util.Scanner;

public class PlacardScreen extends UIScreen{
    SalePlacard placard;
    User user;
    UIScreen previousScreen;

    public PlacardScreen(Scanner scanner, SalePlacard placard, UIScreen previousScreen) {
        super(scanner);
        this.placard = placard;
        this.user = ClientConfiguration.getInstance().getUser();
        this.previousScreen = previousScreen;
    }

    @Override
    void printGuideMessage() {
        UIUtils.header("Placard Details");
        UIUtils.placardTemplate(0, placard, false);
        UIUtils.hr();
        if(isOwnedByUser()){
            UIUtils.secondary("1. Edit placard details");
            if(!isSpecialPlacard())
                UIUtils.secondary("2. Make it special");
        }else{
            UIUtils.options(isUserWish() ? "Remove from wish list" : "Add to wish list");
        }
        UIUtils.secondary("Type 'back' to go back.");
    }

    @Override
    void processInput() {
        String line = scanner.nextLine();
        switch(line){
            case "1" ->{
                if(isOwnedByUser()){
                    new EditPlacardScreen(scanner, placard, previousScreen).guide().process();
                }else{
                    toggleWishStatus();
                }
            }
            case "2" -> {
                if(!isSpecialPlacard()){
                    new PaymentScreen(scanner, this, placard).guide().process();
                }else{
                    this.restartWithError(line + " is not a meaningful command in this context.");
                }
            }
            case "back" -> {
                previousScreen.guide().process();
            }
            default -> {
                this.restartWithError(line + " is not a meaningful command in this context.");
            }
        }
    }

    private boolean isUserWish(){
        //TODO check placard objectId whether is in the user wish list
        return false;
    }

    private void toggleWishStatus(){
        //TODO remove or add placard objectId in user wish list
        trySaveUserObject("The placard " + (isUserWish()? "removed from": "added to")  + " your wish list successfully");
        UIUtils.successful("(press Enter to continue: )");
        scanner.nextLine();
        previousScreen.guide().process();
    }

    private boolean isOwnedByUser(){
        //TODO check placard objectId whether is in the user placard list
        return true;
    }

    private boolean isSpecialPlacard(){
        //TODO check placard is paid to be special
        return false;
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
