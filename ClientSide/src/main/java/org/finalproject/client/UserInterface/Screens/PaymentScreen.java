package org.finalproject.client.UserInterface.Screens;

import org.finalproject.DataObject.SalePlacard;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.UserInterface.BackSupportedInputHandler;
import org.finalproject.client.UserInterface.Navigation;
import org.finalproject.client.UserInterface.UIScreen;
import org.finalproject.client.UserInterface.UIUtils;

import java.util.regex.Pattern;

public class PaymentScreen extends UIScreen {
    private SalePlacard placard;

    public PaymentScreen(SalePlacard placard) {
        super();
        this.placard = placard;
    }

    @Override
    public void startScreen() {
        UIUtils.header("Payment Screen");
        String input = prompt("Enter your card number or 'back' to go back");
        promptInput(new BackSupportedInputHandler() {
            @Override
            public boolean handleValidInput(String input) {
                while (!Pattern.matches("[0-9]{16}", input)) {
                    UIUtils.danger("Invalid card number format. try again");
                    input = scanner.nextLine();
                }
                prompt("Now enter secondary password of your card.");
                try {
                    makeThePlacardSpecial();
                    return true;
                } catch (RequestException ex) {
                    System.out.println("Failed to specialize the placard."+ex.getMessage());
                    return false;
                }
            }
        });


        UIUtils.successful("Payment has done successfully. press Enter to go back");
        scanner.nextLine();
        Navigation.popBackStack();
    }

    void makeThePlacardSpecial() throws RequestException{
        //TODO send related http request to specialize the placard
        // placard object is available as private field
    }
}
