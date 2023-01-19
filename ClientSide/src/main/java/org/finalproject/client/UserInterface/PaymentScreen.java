package org.finalproject.client.UserInterface;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.client.Http.RequestException;

import java.util.Scanner;
import java.util.regex.Pattern;

public class PaymentScreen extends UIScreen{
    private UIScreen previousScreen;
    private SalePlacard placard;
    public PaymentScreen(Scanner scanner, UIScreen previousScreen, SalePlacard placard) {
        super(scanner);
        this.previousScreen = previousScreen;
        this.placard = placard;
    }

    @Override
    void printGuideMessage() {
        UIUtils.header("Payment Screen");
    }

    @Override
    void processInput() {
        String input = getInputBy("Enter your card number or 'back' to go back");
        if(input.equals("back")){
            previousScreen.guide().process();
            return;
        }
        while(!Pattern.matches("[0-9]{16}", input)){
            UIUtils.danger("Invalid card number format. try again");
            input = scanner.nextLine();
        }
        getInputBy("Now enter secondary password of your card.");
        try{
            makeThePlacardSpecial();
        }catch(RequestException ex){
            restartWithError("Failed to specialize the placard." + ex.getMessage());
        }

        UIUtils.successful("Payment has done successfully. press Enter to go back");
        scanner.nextLine();
        previousScreen.guide().process();
    }

    void makeThePlacardSpecial() throws RequestException{
        //TODO send related http request to specialize the placard
        // placard object is available as private field
    }
}
